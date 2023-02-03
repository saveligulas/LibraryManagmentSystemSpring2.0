package com.example.library.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            Customer saveli = new Customer(
                    "Saveli",
                    "saveli.gulas@gmail.com",
                    LocalDate.of(2004,11,18)
            );

            Customer alex = new Customer(
                    "Alex",
                    "alex@dummy.com",
                    LocalDate.of(2000, Month.DECEMBER,24)
            );

            customerRepository.saveAll(
                    List.of(saveli,alex)
            );
        };
    }
}
