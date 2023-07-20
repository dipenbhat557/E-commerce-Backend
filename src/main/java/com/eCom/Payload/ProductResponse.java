package com.eCom.Payload;

import java.util.List;

import com.eCom.Model.Product;

import lombok.Data;

@Data
public class ProductResponse {
    
    private List<Product> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private boolean lastPage; 
}
