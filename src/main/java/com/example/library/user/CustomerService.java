package com.example.library.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        } else {
            customerRepository.save(customer);
        }
    }
}
