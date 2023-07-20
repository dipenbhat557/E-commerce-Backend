package com.eCom.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	private String productName;
	private double productPrice;
	private boolean stock;
	private int productQuantity;
	private boolean live;
	private String productImageName;
	private String productDesc;
	@ManyToOne(fetch = FetchType.EAGER)
    private Category category;
	
}
