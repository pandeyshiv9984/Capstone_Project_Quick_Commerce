package com.mintospeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mintospeed", "com.security"})
public class MintospeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MintospeedApplication.class, args);
	}

}
