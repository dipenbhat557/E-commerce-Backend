package com.eCom.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="`order`")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private String orderStatus;
    private double orderAmount;
    private String paymentStatus;
    private Date orderDelivered;
    private Date orderCreatedAt;
    private String billingAddress;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderStatus=" + orderStatus + ", orderAmount=" + orderAmount
                + ", paymentStatus=" + paymentStatus + ", orderDelivered=" + orderDelivered + ", orderCreatedAt="
                + orderCreatedAt + ", billingAddress=" + billingAddress + ", user=" + user +  "]";
    }
    
}
