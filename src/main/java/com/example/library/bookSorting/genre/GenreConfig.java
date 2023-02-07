package com.example.library.bookSorting.genre;

import com.example.library.user.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenreConfig {
    @Bean
    CommandLineRunner genreCommandLineRunner(GenreRepository genreRepository) {
        return args -> {
        };
    }
}
