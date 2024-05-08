package org.books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.books.controller.dto.BookDto;
import org.books.exception.ResourceNotFoundException;
import org.books.model.Author;
import org.books.model.Book;
import org.books.model.Genre;
import org.books.repository.AuthorRepository;
import org.books.repository.BookRepository;
import org.books.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        bookService = new BookService(bookRepository, authorRepository, genreRepository);
    }

    @Test
    void save(){
        final String isbn="isbn123";
        final String title="Book title";
        final BigDecimal price= BigDecimal.valueOf(12.99);
        final Long genreId=1L;
        final List<Long> authorIds = Stream.of(2L, 1L).toList();
        BookDto bookDto = new BookDto(isbn, title, price, genreId, authorIds);
        final List<Author> authors = Stream.of(
                new Author(2L,"AuthorName2","AuthorSurname2"),
                new Author(1L,"AuthorName1","AuthorSurname1")).toList();
        final Genre genre = new Genre(1L, "sci");
        when(authorRepository.findAllById(bookDto.getAuthorIds())).thenReturn(authors);
        when(genreRepository.findById(bookDto.getGenreId())).thenReturn(Optional.of(genre));
        Book book = new Book(isbn, title, price,  authors, genre);
        when(bookRepository.save(book)).thenReturn(book);
        BookDto response = bookService.save(bookDto);
        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getPrice(), response.getPrice());
        assertEquals(bookDto.getGenreId(), response.getGenreId());
        assertEquals(bookDto.getAuthorIds(), response.getAuthorIds());
    }

    @Test
    void saveGenreNotExist(){
        final String isbn="isbn123";
        final String title="Book title";
        final BigDecimal price= BigDecimal.valueOf(12.99);
        final Long genreId=1L;
        final List<Long> authorIds = Stream.of(2L, 1L).toList();
        BookDto bookDto = new BookDto(isbn, title, price, genreId, authorIds);
        final List<Author> authors = Stream.of(
                new Author(2L,"AuthorName2","AuthorSurname2"),
                new Author(1L,"AuthorName1","AuthorSurname1")).toList();
        final Genre genre = new Genre(1L, "sci");
        when(authorRepository.findAllById(bookDto.getAuthorIds())).thenReturn(authors);
        when(genreRepository.findById(bookDto.getGenreId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> bookService.save(bookDto));
        assertEquals(ResourceNotFoundException.GENRE, exception.getMessage());

    }
}
