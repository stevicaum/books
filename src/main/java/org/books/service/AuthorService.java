package org.books.service;

import org.books.controller.dto.AuthorDto;
import org.books.controller.dto.PageResult;
import org.books.model.Author;
import org.books.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

public class AuthorService {
    private final AuthorRepository authorRepository;
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public PageResult<AuthorDto> getAuthors(final int page, final int size){
        final Page<Author> pageObject = authorRepository.findAll( PageRequest.of( page, size ) );
        return new PageResult<>( pageObject.getTotalPages(), pageObject.getContent().stream().map(
                g -> new AuthorDto.AuthorViewBuilder( g ).build() ).collect( Collectors.toList() ) );
    }
}
