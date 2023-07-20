package com.eCom.Services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eCom.Exception.ResourceNotFoundException;
import com.eCom.Model.Cart;
import com.eCom.Model.CartItem;
import com.eCom.Model.Order;
import com.eCom.Model.OrderItem;
import com.eCom.Model.User;
import com.eCom.Payload.OrderRequest;
import com.eCom.Payload.OrderResponse;
import com.eCom.Repositories.CartRepo;
import com.eCom.Repositories.OrderRepo;
import com.eCom.Repositories.UserRepo;

@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    public Order createOrder(OrderRequest orderRequest, String username) {

        User user = this.userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("The expected user is not found"));

        int cartId = orderRequest.getCartId();
        String orderAddress = orderRequest.getOrderAddress();

        Cart cart = this.cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("The expected cart is not found"));
        List<CartItem> items = cart.getItems();

        Order order = new Order();

        AtomicReference<Double> totalOrderPrice = new AtomicReference<Double>(0.0);

        List<OrderItem> list = items.stream().map((cartItem) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrderQuantity(cartItem.getQuantity());
            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());
            orderItem.getProduct().getProductId();

            return orderItem;
        }).collect(Collectors.toList());

        order.setBillingAddress(orderAddress);
        order.setOrderAmount(totalOrderPrice.get());
        order.setOrderDelivered(null);
        order.setOrderStatus("CREATED");
        order.setPaymentStatus("NOT PAID");
        order.setUser(user);
        order.setOrderItems(list);
        order.setOrderCreatedAt(new Date());

        Order saved;

        if (order.getOrderAmount() > 0) {
            saved = this.orderRepo.save(order);
            cart.getItems().clear();
            this.cartRepo.save(cart);
        } else {
            throw new ResourceNotFoundException("Please order something");
        }

        return saved;
    }

    public void cancelOrder(int orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("The expected order is not found"));
        this.orderRepo.delete(order);
    }

    public Order getById(int orderId){
        return this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("The expected order is not found"));
    }

    public OrderResponse findAllOrders(int pageNumber,int pageSize){

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Order> orders = this.orderRepo.findAll(pageable);
        List<Order> contents = orders.getContent();

        OrderResponse response = new OrderResponse();
        response.setContent(contents);
        response.setPageNumber(orders.getNumber());
        response.setLastPage(orders.isLast());
        response.setPageSize(orders.getSize());
        response.setTotalElement(orders.getTotalElements());
        response.setTotalPage(orders.getTotalPages());

        return response;
    }

}
