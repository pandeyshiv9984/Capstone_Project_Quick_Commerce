package com.mintospeed.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long userId;

 @Column(nullable = false)
 private String name;

 @Column(nullable = false, unique = true)
 private String email;

 @Column(name = "password_hash", nullable = false)
 private String passwordHash;

 private String phone;

 @Column(nullable = false)
 private String role = "CUSTOMER"; // Default role

 @Column(name = "created_at", nullable = false)
 private LocalDateTime createdAt;

 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Cart> carts = new ArrayList<>();

 // Constructors
 public User() {
     this.createdAt = LocalDateTime.now();
 }

 public User(String name, String email, String passwordHash, String phone, String role) {
     this.name = name;
     this.email = email;
     this.passwordHash = passwordHash;
     this.phone = phone;
     this.role = role;
     this.createdAt = LocalDateTime.now();
 }

 public Long getUserId() {
	return userId;
 }

 public void setUserId(Long userId) {
	this.userId = userId;
 }

 public String getName() {
	return name;
 }

 public void setName(String name) {
	this.name = name;
 }

 public String getEmail() {
	return email;
 }

 public void setEmail(String email) {
	this.email = email;
 }

 public String getPasswordHash() {
	return passwordHash;
 }

 public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
 }

 public String getPhone() {
	return phone;
 }

 public void setPhone(String phone) {
	this.phone = phone;
 }

 public String getRole() {
	return role;
 }

 public void setRole(String role) {
	this.role = role;
 }

 public LocalDateTime getCreatedAt() {
	return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
 }

 public List<Cart> getCarts() {
	return carts;
 }

 public void setCarts(List<Cart> carts) {
	this.carts = carts;
 }

 
}