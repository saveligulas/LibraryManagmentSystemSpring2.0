package com.example.library.bookSorting.author;

import com.example.library.bookSorting.book.Book;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    @OneToMany
    @JoinTable(
            name="authors_books",
            joinColumns = @JoinColumn(name = "AUTHOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    private List<Book> books;

    public Author() {

    }

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(Long id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void deleteBook(Book book) {
        this.books.remove(book);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}