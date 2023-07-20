package com.eCom.Payload;

import java.util.List;

import com.eCom.Model.Order;

import lombok.Data;

@Data
public class OrderResponse {

    private int pageSize;
    private int pageNumber;
    private int totalPage;
    private Long totalElement;
    private List<Order> content;
    private boolean isLastPage;
    
}
