package com.example.library.bookSorting.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT s FROM Book s WHERE s.name = ?1")
    Optional<Book> findBookByName(String name);

    @Query("SELECT s FROM Book s WHERE s.id = ?1")
    Book findOneNonOptional(Long id);
}
