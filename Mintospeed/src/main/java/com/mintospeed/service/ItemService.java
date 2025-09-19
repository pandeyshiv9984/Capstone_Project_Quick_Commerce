package com.mintospeed.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mintospeed.dto.ItemRequest;
import com.mintospeed.dto.ItemResponse;
import com.mintospeed.model.Category;
import com.mintospeed.model.Item;
import com.mintospeed.repository.CategoryRepository;
import com.mintospeed.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public ItemResponse addItem(ItemRequest request) {
        Item product = new Item();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setActive(request.getStockQuantity()>0?true:false);
        
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseGet(() -> {

                	//If not, create a new category
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        // Step 3: Assign category to product
        product.setCategory(category);
        
        Item saved = itemRepository.save(product);
        return mapToResponse(saved);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(item);
    }

    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item product = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseGet(() -> {

                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        product.setCategory(category);
        Item updated = itemRepository.save(product);
        return mapToResponse(updated);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemResponse mapToResponse(Item product) {
        ItemResponse response = new ItemResponse();
        response.setItemId(product.getItemId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        return response;
    }
}
