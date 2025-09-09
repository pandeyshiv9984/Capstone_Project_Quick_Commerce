package com.mintospeed.service;

import com.mintospeed.dto.CartResponse;

public interface CartService {
    CartResponse getOrCreateUserCart(Long userId);
    CartResponse addItemToCart(Long userId, Long itemId, Integer quantity);
    CartResponse updateItemQuantity(Long userId, Long itemId, Integer quantity);
    CartResponse removeItemFromCart(Long userId, Long itemId);
    CartResponse getUserCart(Long userId);
    void clearCart(Long userId);
}