package com.mintospeed.security;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
	@NotBlank(message = "email is mandatory")
	private String email;
	
	@NotBlank(message = "password is mandatory")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
