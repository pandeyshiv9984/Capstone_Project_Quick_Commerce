package com.mintospeed.repository;

import com.mintospeed.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    // Find item by name
    Optional<Item> findByName(String name);
    
    // Find items by category
    List<Item> findByCategoryCategoryId(Long categoryId);
    
    // Find items by category name
    List<Item> findByCategoryName(String categoryName);
    
    // Find active items
    List<Item> findByActiveTrue();
    
    // Find items with price less than or equal to
    List<Item> findByPriceLessThanEqual(BigDecimal maxPrice);
    
    // Find items with price between range
    List<Item> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Find items with stock quantity greater than
    List<Item> findByStockQuantityGreaterThan(Integer quantity);
    
    // Find items by name containing (case-insensitive search)
    List<Item> findByNameContainingIgnoreCase(String name);
    
    // Find items by description containing (case-insensitive search)
    List<Item> findByDescriptionContainingIgnoreCase(String description);
    
    // Custom query to find items with low stock
    @Query("SELECT i FROM Item i WHERE i.stockQuantity <= :threshold AND i.active = true")
    List<Item> findLowStockItems(@Param("threshold") Integer threshold);
    
    // Custom query to find items with price range and category
    @Query("SELECT i FROM Item i WHERE i.price BETWEEN :minPrice AND :maxPrice AND i.category.categoryId = :categoryId AND i.active = true")
    List<Item> findItemsByPriceRangeAndCategory(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("categoryId") Long categoryId);
    
    // Check if item exists by name
    boolean existsByName(String name);
}