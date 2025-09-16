package com.mintospeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mintospeed.dto.PasswordChange;
import com.mintospeed.model.User;
import com.mintospeed.repository.UserRepository;


@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/hi")
	public String user(Authentication authentication) {
		System.out.println("hi hit: "+authentication.getName());
		return "User Profile";
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> userProfile(Authentication authentication) {
		System.out.println(authentication);
		String email = authentication.getName();
		System.out.println("Get request is hit for :"+email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User is not authenticated."));
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(@RequestBody User updatedUser, Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User is not authenticated."));
		
		//Update only non sensitive data
		user.setName(updatedUser.getName());
		user.setPhone(updatedUser.getPhone());
		try {
		userRepository.save(user);
		return ResponseEntity.ok("Profile updated successfully.");
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(500);
		}
	}
	
	@PutMapping("/security")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChange password, Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User is not authenticated."));
		
		if(!passwordEncoder.matches(password.getOldPassword(), user.getPasswordHash())) {
			return ResponseEntity.badRequest().body("Old Password is incorrect!");
		}
		
		user.setPasswordHash(passwordEncoder.encode(password.getNewPassword()));
		try {
			userRepository.save(user);
			return ResponseEntity.ok("Password Updated Successfully");
		}catch(Exception e){
			return ResponseEntity.status(500).body("Password cannot be updated now, make sure it is atleast 4 characters long");
		}
	}

}
