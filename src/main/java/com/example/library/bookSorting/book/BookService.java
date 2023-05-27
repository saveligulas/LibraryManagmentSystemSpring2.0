package com.example.library.bookSorting.book;

import com.example.library.bookSorting.author.Author;
import com.example.library.bookSorting.author.AuthorRepository;
import com.example.library.bookSorting.author.AuthorService;
import com.example.library.bookSorting.genre.Genre;
import com.example.library.bookSorting.genre.GenreRepository;
import com.example.library.bookSorting.genre.GenreService;
import com.example.library.user.Customer;
import com.example.library.user.CustomerRepository;
import com.example.library.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CustomerService customerService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookService(BookRepository bookRepository,
                       CustomerService customerService,
                       AuthorService authorService,
                       GenreService genreService) {
        this.bookRepository = bookRepository;
        this.customerService = customerService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public void addNewBook(Book book) {
        Optional<Book> bookByName = bookRepository.findBookByName(book.getName());
        if(bookByName.isPresent()) {
            throw new IllegalStateException("book name taken");
        }
        if(book.getAuthor() != null) {
            authorService.addBookToAuthor(book.getAuthor().getId(),book);
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "book with id " + bookId + " does not exist"));

        customerService.deleteBook(book);

        authorService.deleteBook(book);

        genreService.deleteBook(book);

        bookRepository.deleteById(bookId);
        bookRepository.flush();
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
        bookRepository.flush();
    }



    @Transactional
    public void updateBook(Long bookId, String name,Long genreId, List<Long> genreIdList, Long authorId, Boolean available) {
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

        if(genreId != null &&
                genreId > 0 &&
                genreService.getIdList().contains(genreId)) {
            Genre genre = genreService.getGenre(genreId);
            if(book.getGenres().contains(genre)) {
                throw new IllegalStateException("genre already exists in book");
            }
            if(genre != null) {
                book.addGenre(genre);
            }
        }

        if(genreIdList != null) {
            boolean allGenresExist = true;
            for(Long id:genreIdList) {
                if(allGenresExist) {
                    allGenresExist = genreService.getIdList().contains(genreId);
                }
            }
            if(allGenresExist) {
                List<Genre> genreList = new ArrayList<>();
                for(Long id:genreIdList) {
                    genreList.add(genreService.getGenre(id));
                }
                book.setGenres(genreList);
            }
        }

        if(authorId != null &&
                authorId > 0 &&
                authorService.getAllIds().contains(authorId)) {
            Author author = authorService.getAuthor(authorId);
            if(author != null) {
                Long currentAuthorId = book.getAuthor().getId();
                authorService.deleteBook(book);
                book.setAuthor(author);
                authorService.addBookToAuthor(authorId, book);
            }
        }

        if(available != null) {
            if(Objects.equals(book.getAvailable(), available)) {
                throw new IllegalStateException("book available is already " + available);
            }
            book.setAvailable(available);
        }
    }
}
