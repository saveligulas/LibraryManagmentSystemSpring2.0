package com.example.library.bookSorting.author;

import com.example.library.bookSorting.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorService {

    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void deleteBook(Book book) {
        Optional<Author> authorOptional = authorRepository.findByListContains(book);
        if(authorOptional.isPresent()) {
            Author author = authorRepository.findByListContains(book)
                    .orElseThrow(() -> new IllegalStateException(
                            "no author with book " + book.getName() + " exists"));
            author.deleteBook(book);
        }
    }

}
