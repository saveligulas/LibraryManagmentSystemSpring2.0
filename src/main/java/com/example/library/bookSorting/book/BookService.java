package com.example.library.bookSorting.book;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public void updateBook(Long bookId, String name,Long genreId, List<Long> genreIdList, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "book with id " + bookId + " does not exist"));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(book.getName(), name))  {
            Optional<Book> bookOptional = bookRepository.findBookByName(name);
            if(bookOptional.isPresent()) {
                throw new IllegalStateException("book with name:" + name + "already exists");
            }
            book.setName(name);
        }
    }
}
