package com.mintospeed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long cartItemId;

 @ManyToOne
 @JoinColumn(name = "cart_id")
 private Cart cart;

 @ManyToOne
 @JoinColumn(name = "item_id")
 private Item item;

 private Integer quantity;
 
 public CartItem() {}

 public CartItem(Cart cart, Item item, Integer quantity) {
	this.cart = cart;
	this.item = item;
	this.quantity = quantity;
 }

 public Long getCartItemId() {
	return cartItemId;
 }

 public Cart getCart() {
	return cart;
 }

 public void setCart(Cart cart) {
	this.cart = cart;
 }

 public Item getItem() {
	return item;
 }

 public void setItem(Item item) {
	this.item = item;
 }

 public Integer getQuantity() {
	return quantity;
 }

 public void setQuantity(Integer quantity) {
	this.quantity = quantity;
 }
 
}
