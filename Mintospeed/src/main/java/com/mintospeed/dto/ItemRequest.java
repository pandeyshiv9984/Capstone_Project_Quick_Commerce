package com.mintospeed.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemRequest {
	String name;
	String description;
	String imageUrl;
	BigDecimal price;
	Integer stockQuantity;
	String category;
}
