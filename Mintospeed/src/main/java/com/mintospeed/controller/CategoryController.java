package com.mintospeed.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mintospeed.dto.CategoryDto;
import com.mintospeed.model.Item;
import com.mintospeed.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/{categoryName}")
	public ResponseEntity<CategoryDto> getItemsCategoryWise(@PathVariable("categoryName") String category){
		List<Item> items = categoryService.getAllItemsByCategory(category);
		if(items==null) return  ResponseEntity.notFound().build();
		if(items.size()==0) return ResponseEntity.noContent().build();
		
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category);
		categoryDto.setProducts(items);
		
		return ResponseEntity.ok(categoryDto);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDto> getItemsCategoryWise(@PathVariable("id") Long category){
		List<Item> items = categoryService.getItemsByCategoryId(category);
		if(items==null) return  ResponseEntity.notFound().build();
		if(items.size()==0) return ResponseEntity.noContent().build();
		
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category);
		categoryDto.setName(categoryService.getCategoryById(category).getName());
		categoryDto.setProducts(items);
		
		return ResponseEntity.ok(categoryDto);
	}
}
