package com.example.library;

import com.example.library.user.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@SpringBootApplication
@RestController
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return new Customer(
				"Saveli",
				"saveli.gulas@gmail.com",
				LocalDate.of(2004,11,18),
				18
		).toString();
	}

}
