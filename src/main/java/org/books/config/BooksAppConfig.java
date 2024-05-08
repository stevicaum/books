package org.books.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.books.repository.AuthorRepository;
import org.books.repository.BookRepository;
import org.books.repository.GenreRepository;
import org.books.service.AuthorService;
import org.books.service.BookService;
import org.books.service.GenreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;

@Configuration
public class BooksAppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public BookService bookService(final BookRepository bookRepository, final AuthorRepository authorRepository, final GenreRepository genreRepository){
        return new BookService(bookRepository, authorRepository, genreRepository);
    }

    @Bean
    public GenreService genreService(final GenreRepository genreRepository){
        return new GenreService(genreRepository);
    }

    @Bean
    public AuthorService authorService(final AuthorRepository authorRepository){
        return new AuthorService(authorRepository);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }
}
