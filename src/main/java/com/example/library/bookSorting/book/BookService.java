package com.example.library.bookSorting.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public void addNewBook(Book book) {
        Optional<Book> bookByName = bookRepository.findBookByName(book.getName());
        if(bookByName.isPresent()) {
            throw new IllegalStateException("book name taken");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if(!exists) {
            throw new IllegalStateException("book with id "+ bookId + " does not exist");
        }
        bookRepository.deleteById(bookId);
        bookRepository.flush();
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
        bookRepository.flush();
    }

    public void updateBook(Long bookId) {
    }
}
