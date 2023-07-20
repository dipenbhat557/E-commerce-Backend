package com.eCom.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCom.Model.Cart;
import com.eCom.Payload.ItemRequest;
import com.eCom.Services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody ItemRequest itemRequest, Principal principal){

        String username = principal.getName();

        Cart cart = this.cartService.addItem(itemRequest, username);

        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }
    
    @GetMapping("/view")
    public ResponseEntity<Cart> getAllCart(Principal principal){
        return new ResponseEntity<Cart>(this.cartService.getCartByUser(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/viewById/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable int cartId){

        return new ResponseEntity<Cart>(this.cartService.getCartById(cartId), HttpStatus.OK);
    }

    @GetMapping("remove/{productId}")
    public ResponseEntity<Cart> removeCartItemFromCart(@PathVariable int productId, Principal p){
        Cart newCart = this.cartService.removeCartItem(p.getName(), productId);

        return new ResponseEntity<Cart>(newCart, HttpStatus.OK);
    }
}
