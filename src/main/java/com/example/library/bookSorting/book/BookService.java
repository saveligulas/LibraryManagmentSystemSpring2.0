package com.example.library.bookSorting.book;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.author.AuthorRepository;
import com.example.library.bookSorting.genre.Genre;
import com.example.library.bookSorting.genre.GenreRepository;
import com.example.library.user.Customer;
import com.example.library.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       CustomerRepository customerRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
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

    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "book with id " + bookId + " does not exist"));
        Optional<Customer> customerOptional = customerRepository.findByListContainsBook(book);
        if(customerOptional.isPresent()) {
            Customer customer = customerRepository.findByListContainsBook(book)
                    .orElseThrow(() -> new IllegalStateException(
                            "no customer with book id " + bookId + " in his books exists"));
            customer.deleteBook(book);
        }
        Optional<Author> authorOptional = authorRepository.findByListContains(book);
        if(authorOptional.isPresent()) {
            Author author = authorRepository.findByListContains(book)
                    .orElseThrow(() -> new IllegalStateException(
                            "no author with book id " + bookId + " exists"));
            author.deleteBook(book);
        }
        Optional<Genre> genreOptional = genreRepository.findByListContainsBook(book);
        if(genreOptional.isPresent()) {
            Genre genre = genreRepository.findByListContainsBook(book)
                    .orElseThrow(() -> new IllegalStateException(
                            "no genre with book id " + bookId + " exists"));
            genre.deleteBook(book);
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
