package com.example.library.bookSorting.genre;

import com.example.library.bookSorting.book.Book;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name="genre_books",
            joinColumns = @JoinColumn(name = "GENRE_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    private List<Book> books;

    public Genre() {

    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(Long id, String name, List<Book> books) {
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

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void deleteBook(Book book) {
        this.books.remove(book);
    }
}