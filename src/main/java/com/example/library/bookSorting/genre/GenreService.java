package com.example.library.bookSorting.genre;

import com.example.library.bookSorting.book.Book;
import com.example.library.bookSorting.book.BookRepository;
import com.example.library.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository,
                        BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public void addNewGenre(Genre genre) {
        Optional<Genre> genreByName = genreRepository.findByName(genre.getName());
        if(genreByName.isPresent()) {
            throw new IllegalStateException("genre already exists");
        }
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreId) {
        boolean exists = genreRepository.existsById(genreId);
        if(!exists) {
            throw new IllegalStateException("genre with " + genreId + " id does not exist");
        }
        genreRepository.deleteById(genreId);
    }

    @Transactional
    public void deleteBook(Book book) {
        Optional<Genre> genreOptional = genreRepository.findByListContainsBook(book);
        if(genreOptional.isPresent()) {
            Genre genre = genreRepository.findByListContainsBook(book)
                    .orElseThrow(() -> new IllegalStateException(
                            "no genre with book " + book.getName() + " exists"));
            genre.deleteBook(book);
        }
    }

    @Transactional
    public void updateGenre(Long genreId, String name, Book book) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalStateException(
                        "genre with id " + genreId + " does not exist"));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(genre.getName(), name)) {
            Optional<Genre> genreOptional = genreRepository.findByName(name);
            if(genreOptional.isPresent()) {
                throw new IllegalStateException("name is already taken");
            }
            genre.setName(name);
        }

        if(book != null) {
            Optional<Genre> genreOptional = genreRepository.findByListContainsBook(book);
            boolean isEqual = genreOptional.equals(Optional.of(genre));
            if(genreOptional.isPresent() &&
                    isEqual) {
                genre.deleteBook(book);
            }
            if(genreOptional.isEmpty()) {
                genre.addBook(book);
            }
        }
    }
}
