package com.example.library.bookSorting.book;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.genre.Genre;
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

    @PutMapping(path = "{bookId}") //Not working yet
    public void updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) List<Long> genreIdList,
            @RequestParam(required = false) Long authorId) {
        bookService.updateBook(bookId,name,genreId,genreIdList,authorId);
    }
}
