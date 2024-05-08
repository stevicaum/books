package org.books.service;

import org.books.controller.dto.BookNoIsbnDto;
import org.books.controller.dto.BookDto;
import org.books.controller.dto.PageResult;
import org.books.exception.ResourceNotFoundException;
import org.books.model.Author;
import org.books.model.Book;
import org.books.model.Genre;
import org.books.repository.AuthorRepository;
import org.books.repository.BookRepository;
import org.books.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(final BookRepository bookRepository, final AuthorRepository authorRepository, final GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public BookDto store(final BookDto bookDto) {
        final Optional<Genre> genre = getGenreAndValidate(bookDto);
        final List<Author> authors = getAuthorsAndValidate(bookDto);
        final Book book = bookRepository.save(new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getPrice(), authors, genre.get()));
        return mapBook(book);
    }

    public BookDto update(final String isbn, final BookNoIsbnDto bookDto) {
        final Optional<Genre> genre = getGenreAndValidate(bookDto);
        final List<Author> authors = getAuthorsAndValidate(bookDto);
        final Optional<Book> response = bookRepository.findById(isbn);
        if (!response.isPresent()) {
            throw new ResourceNotFoundException(String.format(ResourceNotFoundException.BOOK, isbn));
        }
        final Book book = bookRepository.save(new Book(isbn, bookDto.getTitle(), bookDto.getPrice(), authors, genre.get()));
        return mapBook(book);
    }

    private List<Author> getAuthorsAndValidate(BookNoIsbnDto bookDto) {
        final List<Author> authors = authorRepository.findAllById(bookDto.getAuthorIds());
        if (authors.isEmpty() || bookDto.getAuthorIds().size() != authors.size()) {
            throw new ResourceNotFoundException(ResourceNotFoundException.AUTHORS);
        }
        return authors;
    }

    private Optional<Genre> getGenreAndValidate(BookNoIsbnDto bookDto) {
        final Optional<Genre> genre = genreRepository.findById(bookDto.getGenreId());
        if (!genre.isPresent()) {
            throw new ResourceNotFoundException(String.format(ResourceNotFoundException.GENRE, bookDto.getGenreId()));
        }
        return genre;
    }

    public void delete(final String isbn) {
        final Optional<Book> response = bookRepository.findById(isbn);
        if (!response.isPresent()) {
            throw new ResourceNotFoundException(String.format(ResourceNotFoundException.BOOK, isbn));
        }
        bookRepository.delete(response.get());
    }

    public PageResult<BookDto> searchBook(final String title, final String author, final String genre, final int page, final int size) {
        final Page<Book> pageObject = bookRepository.findBooksBy(title, author, genre, PageRequest.of(page, size));
        return new PageResult<>(pageObject.getTotalPages(),
                pageObject.stream().map(o -> mapBook(o)).collect(Collectors.toList()));
    }

    private BookDto mapBook(final Book book) {
        return  new BookDto(book.getIsbn(), book.getTitle(), book.getPrice(), book.getGenre().getId(),
                book.getAuthors().stream().map(a -> a.getId()).collect(Collectors.toList()));
    }
//
//    public BookView getBook( final String isbn ) {
//        final Book book = bookRepository.findWithPreFetched( isbn );
//        if ( book == null ) {
//            throw new ResourceNotFoundException( ResourceNotFoundException.BOOK + isbn );
//        }
//        return new BookView.BookViewBuilder( book ).build();
//    }

}
