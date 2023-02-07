package com.example.library.bookSorting.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService,
                          BookRepository bookRepository) {
        this.bookService = bookService;
                this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getBooks() {
        List<Book> optionalBookList = bookService.getBooks();
        if(optionalBookList.isEmpty()) {
            throw new IllegalStateException("no books located in the database");
        }
        return bookService.getBooks();
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book) {
        bookService.addNewBook(book);
    }

    @DeleteMapping(path="{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

    @DeleteMapping(path="0")
    public void deleteAllBooks() {
        bookService.deleteAllBooks();
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long bookId) {
        bookService.updateBook(bookId);
    }
}
