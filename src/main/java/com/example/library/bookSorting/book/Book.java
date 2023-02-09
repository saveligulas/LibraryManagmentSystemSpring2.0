package com.example.library.bookSorting.book;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.genre.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    @ManyToOne
    private Author author;
    private String publisher;
    private Integer year;
    private Boolean available;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dor;
    @ManyToMany
    @JoinTable(
            name="books_genres",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )
    private List<Genre> genres;

    public Book() {
    }

    public Book(String name, String publisher, Integer year) {
        this.name = name;
        this.publisher = publisher;
        this.year = year;
        this.available = true;
    }

    public Book(String name, Author author, String publisher, Integer year) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = true;
    }

    public Book(String name, Author author,String publisher, Integer year, Boolean available) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
    }

    public Book(Long id, String name, Author author,String publisher, Integer year, Boolean available) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
    }

    public Book(Long id, String name, Author author,String publisher, Integer year, Boolean available, LocalDate dor) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
        this.dor = dor;
    }

    public Book(Long id, String name, Author author, String publisher, Integer year, Boolean available, LocalDate dor, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
        this.dor = dor;
        this.genres = genres;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public LocalDate getDor() {
        return dor;
    }

    public void setDor(LocalDate dor) {
        this.dor = dor;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", available=" + available +
                ", dor=" + dor +
                '}';
    }
}
