package com.mintospeed.dto;

import java.math.BigDecimal;

import com.mintospeed.model.Category;

import lombok.Data;

@Data
public class ItemResponse {
	Long itemId;
	String name;
	String description;
	String imageUrl;
	BigDecimal price;
	Integer stockQuantity;
	Category category;
	boolean active;
}
