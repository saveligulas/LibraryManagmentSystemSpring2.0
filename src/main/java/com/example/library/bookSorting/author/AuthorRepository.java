package com.example.library.bookSorting.author;

import com.example.library.bookSorting.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE :book MEMBER OF e.books")
    Optional<Author> findByListContains(@Param("book") Book book);
}