package org.books.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.books.controller.dto.BookDto;
import org.books.controller.dto.BookNoIsbnDto;
import org.books.controller.dto.PageResult;
import org.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(BookController.BOOKS_ENDPOINT)
@Tag(name = BookController.BOOKS_ENDPOINT)
@Validated
public class BookController {

    public static final String BOOKS_ENDPOINT = "/books";
    public static final String PAGE_SHOULD_BE_GREATER_THEN_0 = "not valid, should be greater then 0";
    public static final String PAGE = "page";
    public static final String SIZE = "size";

    public static final String ISBN = "isbn";
    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto saveBook(@Valid @RequestBody final BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping(value = "{" + ISBN + "}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto updateBook(@PathVariable(ISBN) @NotBlank String isbn,
                              @Valid @RequestBody final BookNoIsbnDto bookDto) {
        return bookService.update(isbn, bookDto);
    }

    @DeleteMapping(value = "{" + ISBN + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable(ISBN) @NotBlank String isbn) {
        bookService.delete(isbn);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public PageResult<BookDto> searchBook(@RequestParam(required = false) String title,
                                          @RequestParam(required = false) String author,
                                          @RequestParam(required = false) String genre,
                                          @RequestParam(value = PAGE, defaultValue = "0") final int page,
                                          @RequestParam(value = SIZE, defaultValue = "10") final int size) {
        return bookService.searchBook(title, author, genre, page, size);
    }

}
