package com.example.library.bookSorting.author;

import com.example.library.bookSorting.book.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorConfig {

    @Bean
    CommandLineRunner authorCommandLineRunner(AuthorRepository authorRepository) {
        return args -> {
        };
    }
}
