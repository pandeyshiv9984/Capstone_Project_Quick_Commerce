package com.mintospeed.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

//Cart.java
@Entity
@Table(name = "carts")
public class Cart {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long cartId;

 @OneToOne
 @JoinColumn(name = "user_id")
 private User user;

 @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<CartItem> cartItems = new ArrayList<>();

 @Column(name = "created_at")
 private LocalDateTime createdAt;

 // Constructors, getters, and setters
 public Cart() {
     this.createdAt = LocalDateTime.now();
 }

 public Long getCartId() {
	return cartId;
 }

 public User getUser() {
	return user;
 }

 public void setUser(User user) {
	this.user = user;
 }

 public List<CartItem> getCartItems() {
	return cartItems;
 }

 public void setCartItems(List<CartItem> cartItems) {
	this.cartItems = cartItems;
 }

 public LocalDateTime getCreatedAt() {
	return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
 }
 
 
}