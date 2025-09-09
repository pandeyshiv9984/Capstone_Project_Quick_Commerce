package com.mintospeed.repository;

import com.mintospeed.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    Optional<CartItem> findByCartCartIdAndItemItemId(Long cartId, Long itemId);
    
    boolean existsByCartCartIdAndItemItemId(Long cartId, Long itemId);
    
    List<CartItem> findByCartCartId(Long cartId);
    
    void deleteByCartCartIdAndItemItemId(Long cartId, Long itemId);
    
    void deleteByCartCartId(Long cartId);
}