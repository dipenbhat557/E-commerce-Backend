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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCom.Model.Order;
import com.eCom.Payload.ApiResponse;
import com.eCom.Payload.OrderRequest;
import com.eCom.Payload.OrderResponse;
import com.eCom.Services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody OrderRequest orderRequest, Principal principal) {

        Order order = this.orderService.createOrder(orderRequest, principal.getName());

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);

    }

    @GetMapping("delete/{orderId}")
    public ResponseEntity<?> cancelOrderById(@PathVariable int orderId) {
        this.orderService.cancelOrder(orderId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Order Deleted....", true), HttpStatus.OK);
    }

    @GetMapping("viewById/{orderId}")
    public ResponseEntity<Order> getById(@PathVariable int orderId) {
        return new ResponseEntity<Order>(this.orderService.getById(orderId), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<OrderResponse> findAllOrders(
            @RequestParam(defaultValue = "2", value = "pageSize") int pageSize,
            @RequestParam(defaultValue = "0", value = "pageNumber")int pageNumber) {

        return new ResponseEntity<OrderResponse>(this.orderService.findAllOrders(pageNumber, pageSize), HttpStatus.OK);
    }

}
