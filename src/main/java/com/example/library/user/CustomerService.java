package com.example.library.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    public List<Customer> getCustomers() {
        return List.of(
                new Customer(
                        "Test",
                        "Test@gmail.com",
                        LocalDate.of(2004,11,18),
                        18
                )
        );
    }
}
