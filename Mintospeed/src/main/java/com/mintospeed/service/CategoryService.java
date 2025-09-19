package com.mintospeed.service;

import com.mintospeed.model.Category;
import com.mintospeed.model.Item;
import com.mintospeed.repository.CategoryRepository;
import com.mintospeed.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
	
	@Autowired
	private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryByName(String name) {
    	return categoryRepository.findByName(name);
    }
    
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category existing = getCategoryById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }
    
    // Get all products of a perticular category
    public List<Item> getAllItemsByCategory(String categoryName) {
    	return itemRepository.findByCategoryName(categoryName);
    }

    // Get items by category ID
    public List<Item> getItemsByCategoryId(Long categoryId) {
        return itemRepository.findByCategoryCategoryId(categoryId);
    }
}
