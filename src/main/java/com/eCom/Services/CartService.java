package com.eCom.Services;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCom.Exception.ResourceNotFoundException;
import com.eCom.Model.Cart;
import com.eCom.Model.CartItem;
import com.eCom.Model.Product;
import com.eCom.Model.User;
import com.eCom.Payload.ItemRequest;
import com.eCom.Repositories.CartRepo;
import com.eCom.Repositories.ProductRepo;
import com.eCom.Repositories.UserRepo;

@Service
public class CartService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    public Cart addItem(ItemRequest item, String username) {

        int pId = item.getProductId();
        int quantity = item.getQuantity();
        // fetch user
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("The expected user does not exist"));
        // fetch product
        Product product = this.productRepo.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("The product is out of stock"));

        // checking stock of product
        if (!product.isStock()) {
            new ResourceNotFoundException("Product out of Stock");
        }

        // create cartItem with pId and quantity
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(product.getProductPrice() * quantity);

        // getting cart from user
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
        }

        cartItem.setCart(cart);
        List<CartItem> items = cart.getItems();
        System.out.println(item);

        // check is already in cart, if yes then increasing quantity else add new item
        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        List<CartItem> newProduct = items.stream().map(i -> {
            if (i.getProduct().getProductId() == product.getProductId()) {
                i.setQuantity(quantity);
                i.setTotalPrice(product.getProductPrice() * quantity);
                flag.set(true);
            }
            return i;
        }).collect(Collectors.toList());

        if(flag.get()){
            items.clear();
            items.addAll(newProduct);
        }else{
            cartItem.setCart(cart);
            items.add(cartItem);
        }

        cart.setUser(user);

        Cart addedCart = this.cartRepo.save(cart);

        return addedCart;
    }

    public Cart getCartByUser(String email){
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("The expected user does not exist"));
        return this.cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("There is no cart"));
    }

    public Cart getCartById(int cartId){
      
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("The expectd cart is not present"));

        return cart;
    }

    public Cart removeCartItem(String username, int productId){
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("The expected user does not exist"));

        Cart cart = user.getCart();

        List<CartItem> items = cart.getItems();

        boolean remove = items.removeIf((i)->{
            return i.getProduct().getProductId() == productId;
        });
        Cart newCart = this.cartRepo.save(cart);
        System.out.println("The removed one is " + remove);

        return newCart;
    }
}
