package com.example.library.user;

import com.example.library.bookSorting.book.Book;
import com.example.library.bookSorting.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if(!exists) {
            throw new IllegalStateException("customer with id "+ customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
        customerRepository.flush();
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
        customerRepository.flush();
    }

    @Transactional
    public void updateCustomer(Long customerId, String name, String email, Long bookId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerId + " does not exist"));

        if (name != null &&
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

        if (bookId != null &&
                !customer.getBooks().contains(bookRepository.findOneNonOptional(bookId))) {
            Optional<Book> bookOptional = bookRepository.
                    findById(bookId);
            if(bookOptional.isEmpty()) {
                throw new IllegalStateException("book does not exist");
            }
            customer.setBooks(List.of(bookRepository.findOneNonOptional(bookId)));
        }
    }
}
