package com.mintospeed.repository;

import com.mintospeed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email
    Optional<User> findByEmail(String email);
    
    // Find user by phone number
    Optional<User> findByPhone(String phone);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Check if phone number exists
    boolean existsByPhone(String phone);
    
    // Find users by role
    List<User> findByRole(String role);
    
    // Custom query to find users created after a certain date
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
    List<User> findUsersCreatedAfter(@Param("date") LocalDateTime date);
    
    // Find users by name containing (case-insensitive)
    List<User> findByNameContainingIgnoreCase(String name);
}