package org.books.controller;

import org.books.controller.dto.AuthorDto;
import org.books.controller.dto.PageResult;
import org.books.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.books.controller.GenreController.PAGE;
import static org.books.controller.GenreController.SIZE;

@RestController
public class AuthorController {
    public static final String AUTHORS_ENDPOINT = "/authors";
    private final AuthorService authorService;

    public AuthorController( final AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping ( value = AUTHORS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public PageResult<AuthorDto> getAuthors(@RequestParam(value = PAGE, defaultValue = "0") final int page,
                   @RequestParam(value = SIZE, defaultValue = "10") final  int size ) {
        return authorService.getAuthors( page, size ) ;
    }
}
