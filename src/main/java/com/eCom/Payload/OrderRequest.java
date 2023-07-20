package com.eCom.Payload;

import lombok.Data;

@Data
public class OrderRequest {

    private String orderAddress;
    private int cartId;
    
}
