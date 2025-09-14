package com.mintospeed.security;

import jakarta.validation.constraints.*;

public class SignupRequestDto {
	
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message= "Name is required!")
	private String name;
	@Size(min = 4, message = "Password must be atleast 4 characters")
	private String password;
	@Digits(fraction = 0, integer = 10)
	private long phone;
	private String role;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
