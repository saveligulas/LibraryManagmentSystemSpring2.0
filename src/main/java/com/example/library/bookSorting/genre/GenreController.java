package com.example.library.bookSorting.genre;

import com.example.library.user.Customer;
import com.example.library.user.CustomerRepository;
import com.example.library.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/genre")
public class GenreController {
    private final GenreService genreService;
    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreService genreService, GenreRepository genreRepository) {
        this.genreService = genreService;
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @PostMapping
    public void registerNewGenre(@RequestBody Genre genre) {
        genreService.addNewGenre(genre);
    }

    @DeleteMapping(path="{genreId}")
    public void deleteCustomer(@PathVariable("genreId") Long genreId) {
        genreService.deleteGenre(genreId);
    }

    @PutMapping(path = "{genreId}")
    public void updateGenre(
            @PathVariable("genreId") Long genreId,
            @RequestParam(required = false) String name) {
        genreService.updateGenre(genreId,name);
    }
}
