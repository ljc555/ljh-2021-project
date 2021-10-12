package com.ljc555;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SearchHouseNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchHouseNetApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello, wali";
	}
}
