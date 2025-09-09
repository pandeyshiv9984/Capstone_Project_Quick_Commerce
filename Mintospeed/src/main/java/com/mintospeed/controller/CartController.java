package com.mintospeed.controller;

import com.mintospeed.dto.CartResponse;
import com.mintospeed.dto.CartItemRequest;
import com.mintospeed.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @PostMapping("/item")
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(
            request.getUserId(), 
            request.getItemId(), 
            request.getQuantity()
        ));
    }

    @PutMapping("/item")
    public ResponseEntity<CartResponse> updateItemQuantity(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.updateItemQuantity(
            request.getUserId(), 
            request.getItemId(), 
            request.getQuantity()
        ));
    }

    @DeleteMapping("/item")
    public ResponseEntity<CartResponse> removeItemFromCart(
            @RequestParam Long userId,
            @RequestParam Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, itemId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}