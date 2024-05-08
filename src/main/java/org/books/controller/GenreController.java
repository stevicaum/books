package org.books.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.books.controller.dto.GenreDto;
import org.books.controller.dto.PageResult;
import org.books.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = GenreController.GENRES_ENDPOINT)
public class GenreController {
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String GENRES_ENDPOINT = "/genres";
    private final GenreService genreService;

    public GenreController(final GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = GENRES_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public PageResult<GenreDto> getGenres(@RequestParam(value = PAGE, defaultValue = "0") final int page,
                                          @RequestParam(value = SIZE, defaultValue = "10") final  int size) {
        return genreService.getGenres(page, size);
    }
}
