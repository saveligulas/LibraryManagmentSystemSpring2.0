package com.example.library.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if(!exists) {
            throw new IllegalStateException("customer with id "+ customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @Transactional
    public void updateCustomer(Long customerId, String name, String email) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerId + " does not exist"));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(customer.getName(), name)) {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByName(name);
            if(customerOptional.isPresent()) {
                throw new IllegalStateException("name taken");
            }
            customer.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByEmail(email);
            if(customerOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            customer.setEmail(email);
        }
    }
}
