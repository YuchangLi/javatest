package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GreenwichWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenwichWebApplication.class, args);
	}

	
}
