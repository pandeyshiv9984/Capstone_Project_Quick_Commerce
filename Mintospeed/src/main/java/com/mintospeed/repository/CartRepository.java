package com.mintospeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mintospeed.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
 Optional<Cart> findByUserUserId(Long userId);
 Optional<Cart> findByUserEmail(String email);
}
