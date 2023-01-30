package com.example.library;

import com.example.library.user.Customer;
import org.joda.time.LocalDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@GetMapping("/")
	public List<Customer> hello() {
		return List.of(
				new Customer(
						1L,
						"Saveli",
						"saveli.gulas@gmail.com",
						LocalDate.now(),
						18
				)
		);
	}

}
