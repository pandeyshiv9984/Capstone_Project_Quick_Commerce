package com.mintospeed.dto;

import java.util.List;

import com.mintospeed.model.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	Long id;
	String name;
	List<Item> products;
}
