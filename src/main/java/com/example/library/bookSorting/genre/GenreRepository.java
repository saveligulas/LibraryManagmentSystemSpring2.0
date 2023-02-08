package com.example.library.bookSorting.genre;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.book.Book;
import com.example.library.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT g FROM Genre g WHERE g.name = ?1")
    Optional<Genre> findByName(String name);

    @Query("SELECT g FROM Genre g WHERE :book MEMBER OF g.books")
    Optional<Genre> findByListContainsBook(@Param("book") Book book);
}
