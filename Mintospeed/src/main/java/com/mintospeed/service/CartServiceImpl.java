package com.mintospeed.service;

import com.mintospeed.dto.CartResponse;
import com.mintospeed.dto.CartItemResponse;
import com.mintospeed.model.*;
import com.mintospeed.repository.*;
import com.mintospeed.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, 
                          CartItemRepository cartItemRepository,
                          ItemRepository itemRepository,
                          UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponse getOrCreateUserCart(Long userId) {
        Cart cart = getOrCreateUserCartEntity(userId);
        return convertToCartResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(Long userId, Long itemId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = getOrCreateUserCartEntity(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));

        // Check stock availability
        if (item.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for item: " + item.getName());
        }

        // FIXED: Use the correct repository method that returns Optional
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartCartIdAndItemItemId(cart.getCartId(), itemId);
        CartItem cartItem;
        
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
        }

        cartItemRepository.save(cartItem);
        
        // Refresh cart to get updated items
        Cart updatedCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found after update"));
        
        return convertToCartResponse(updatedCart);
    }

    @Override
    public CartResponse updateItemQuantity(Long userId, Long itemId, Integer quantity) {
        Cart cart = getOrCreateUserCartEntity(userId);
        
        // FIXED: Use the correct repository method
        CartItem cartItem = cartItemRepository.findByCartCartIdAndItemItemId(cart.getCartId(), itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            // Check stock availability for the new quantity
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
            
            if (item.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for item: " + item.getName());
            }
            
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        Cart updatedCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found after update"));
        
        return convertToCartResponse(updatedCart);
    }

    @Override
    public CartResponse removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateUserCartEntity(userId);
        
        // Check if item exists in cart before deleting
        // FIXED: Use the correct repository method
        if (!cartItemRepository.existsByCartCartIdAndItemItemId(cart.getCartId(), itemId)) {
            throw new ResourceNotFoundException("Item not found in cart");
        }
        
        // FIXED: Use the correct delete method
        cartItemRepository.deleteByCartCartIdAndItemItemId(cart.getCartId(), itemId);
        
        Cart updatedCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found after update"));
        
        return convertToCartResponse(updatedCart);
    }

    @Override
    public CartResponse getUserCart(Long userId) {
        Cart cart = getOrCreateUserCartEntity(userId);
        return convertToCartResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreateUserCartEntity(userId);
        // FIXED: Use the correct delete method
        List<CartItem> cartItems = cartItemRepository.findByCartCartId(cart.getCartId());
        cartItemRepository.deleteAll(cartItems);
    }

    // Helper method to get or create cart entity
    private Cart getOrCreateUserCartEntity(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return cartRepository.findByUserUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    // Helper method to convert Cart entity to CartResponse DTO
    private CartResponse convertToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());
        
        // Convert cart items to response DTOs
        List<CartItemResponse> itemResponses = cart.getCartItems().stream()
                .map(this::convertToCartItemResponse)
                .collect(Collectors.toList());
        
        response.setItems(itemResponses);
        
        // Calculate total items and total price
        int totalItems = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        response.setTotalItems(totalItems);
        response.setTotalPrice(totalPrice.doubleValue());
        
        return response;
    }

    // Helper method to convert CartItem entity to CartItemResponse DTO
    private CartItemResponse convertToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setItemId(cartItem.getItem().getItemId());
        response.setItemName(cartItem.getItem().getName());
        response.setItemImageUrl(cartItem.getItem().getImageUrl());
        response.setItemPrice(cartItem.getItem().getPrice());
        response.setQuantity(cartItem.getQuantity());
        
        // Calculate subtotal
        BigDecimal subtotal = cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        response.setSubtotal(subtotal);
        
        return response;
    }
}