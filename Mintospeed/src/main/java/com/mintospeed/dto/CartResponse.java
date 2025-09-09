package com.mintospeed.dto;

import java.util.List;

public class CartResponse {
    private Long cartId;
    private List<CartItemResponse> items;
    private Integer totalItems;
    private Double totalPrice;

    public CartResponse() {}

    public CartResponse(Long cartId, List<CartItemResponse> items, Integer totalItems, Double totalPrice) {
        this.cartId = cartId;
        this.items = items;
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "cartId=" + cartId +
                ", items=" + items +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}