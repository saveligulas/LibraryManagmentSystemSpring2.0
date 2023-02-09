package com.example.library.bookSorting.author;

import com.example.library.bookSorting.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public void addNewAuthor(Author author) {
        Optional<Author> authorOptional = authorRepository.findAuthorByName(author.getName());
        if(authorOptional.isPresent()) {
            throw new IllegalStateException("author with name " + author.getName() + "already exists");
        }
        authorRepository.save(author);
    }
}
