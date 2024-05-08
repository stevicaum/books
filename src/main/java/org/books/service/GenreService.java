package org.books.service;

import org.books.controller.dto.GenreDto;
import org.books.controller.dto.PageResult;
import org.books.model.Genre;
import org.books.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

public class GenreService {
    private final GenreRepository genreRepository;
    public GenreService(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public PageResult<GenreDto> getGenres(final int page, final int size){
        final Page<Genre> pageObject = genreRepository.findAll( PageRequest.of( page, size ) );
        return new PageResult<>( pageObject.getTotalPages(), pageObject.getContent().stream().map(
                g -> new GenreDto.GenreViewBuilder( g ).build() ).toList());
    }
}
