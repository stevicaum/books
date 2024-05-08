package org.books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.books.controller.dto.BookDto;
import org.books.controller.dto.PageResult;
import org.books.exception.ResourceNotFoundException;
import org.books.repository.AuthorRepository;
import org.books.repository.BookRepository;
import org.books.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookServiceIT {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        bookService = new BookService(bookRepository, authorRepository, genreRepository);
    }

    @Test
    @Sql("/sql/saveBook.sql")
    void saveBook() {
        assertEquals(0, bookRepository.count());
        final String isbn="isbn123";
        final String title="Book title";
        final BigDecimal price= BigDecimal.valueOf(12.99);
        final Long genreId=1L;
        final List<Long> authorIds = Stream.of(2L, 1L).toList();
        BookDto bookDto = new BookDto(isbn, title, price, genreId, authorIds);
        BookDto response = bookService.save(bookDto);
        assertEquals(1, bookRepository.count());
        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getPrice(), response.getPrice());
        assertEquals(bookDto.getGenreId(), response.getGenreId());
        assertEquals(2,response.getAuthorIds().size());
        assertTrue(response.getAuthorIds().containsAll(Stream.of(2L, 1L).toList()));
    }

    @Test
    @Sql("/sql/saveBook.sql")
    void saveBookAuthorNotExist() {
        assertEquals(0, bookRepository.count());
        final String isbn="isbn123";
        final String title="Book title";
        final BigDecimal price= BigDecimal.valueOf(12.99);
        final Long genreId=1L;
        final List<Long> authorIds = Stream.of(2L, 11L).toList();
        BookDto bookDto = new BookDto(isbn, title, price, genreId, authorIds);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> bookService.save(bookDto));
        assertEquals(ResourceNotFoundException.AUTHORS, exception.getMessage());
    }

    @Test
    @Sql("/sql/searchBook.sql")
    void searchBookTitle() {
        assertEquals(3, authorRepository.count());
        assertEquals(2, genreRepository.count());
        PageResult<BookDto> pageResult = bookService.searchBook("title1","notexist", null, 0,10);
        assertEquals(1, pageResult.getTotalPages());
        assertEquals(1, pageResult.getItems().size());
        assertEquals(22L, pageResult.getItems().get(0).getGenreId());
        assertEquals(BigDecimal.valueOf(12.99), pageResult.getItems().get(0).getPrice());
        assertEquals("isbn123", pageResult.getItems().get(0).getIsbn());
        assertEquals(2, pageResult.getItems().get(0).getAuthorIds().size());
        assertTrue(pageResult.getItems().get(0).getAuthorIds().containsAll(Stream.of(12L, 11L).toList()));
    }

    @Test
    @Sql("/sql/searchBook.sql")
    void searchBookAuthor() {
        PageResult<BookDto> pageResult = bookService.searchBook(null,"tim", "notexist", 0,10);
        assertEquals(1, pageResult.getTotalPages());
        assertEquals(1, pageResult.getItems().size());
        assertEquals(12L, pageResult.getItems().get(0).getGenreId());
        assertEquals(BigDecimal.valueOf(13.99), pageResult.getItems().get(0).getPrice());
        assertEquals("isbn4", pageResult.getItems().get(0).getIsbn());
        assertEquals(2, pageResult.getItems().get(0).getAuthorIds().size());
        assertTrue(pageResult.getItems().get(0).getAuthorIds().containsAll(Stream.of(13L, 11L).toList()));
    }


    @Test
    @Sql("/sql/searchBook.sql")
    void searchBookGenre() {
        PageResult<BookDto> pageResult = bookService.searchBook(null,"test", "sci", 0,10);
        assertEquals(1, pageResult.getTotalPages());
        assertEquals(1, pageResult.getItems().size());
        assertEquals(12L, pageResult.getItems().get(0).getGenreId());
        assertEquals(BigDecimal.valueOf(13.99), pageResult.getItems().get(0).getPrice());
        assertEquals("isbn4", pageResult.getItems().get(0).getIsbn());
        assertEquals(2, pageResult.getItems().get(0).getAuthorIds().size());
        assertTrue(pageResult.getItems().get(0).getAuthorIds().containsAll(Stream.of(13L, 11L).toList()));
    }

}
