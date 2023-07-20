package com.eCom.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cardItemId;
    private int quantity;
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    @OneToOne( orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Product product;

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + this.getCardItemId() +
                ", quantity=" + this.getQuantity() +
                ", totalPrice=" + this.getTotalPrice() +
                '}';
    }

}
