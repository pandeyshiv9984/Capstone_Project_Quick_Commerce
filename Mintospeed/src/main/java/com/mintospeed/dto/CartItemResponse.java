package com.mintospeed.dto;

import java.math.BigDecimal;

public class CartItemResponse {
    private Long cartItemId;
    private Long itemId;
    private String itemName;
    private String itemImageUrl;
    private BigDecimal itemPrice;
    private Integer quantity;
    private BigDecimal subtotal;

    public CartItemResponse() {}

    public CartItemResponse(Long cartItemId, Long itemId, String itemName, String itemImageUrl, 
                           BigDecimal itemPrice, Integer quantity, BigDecimal subtotal) {
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "CartItemResponse{" +
                "cartItemId=" + cartItemId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemImageUrl='" + itemImageUrl + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}