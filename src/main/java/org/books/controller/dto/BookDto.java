package org.books.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BookDto extends BookNoIsbnDto {

    @NotBlank(message = "This field can't be empty")
    private String isbn;

    protected BookDto() {

    }

    public String getIsbn() {
        return isbn;
    }

    public BookDto(final String isbn, final String title, final BigDecimal price, final Long genre,
                   final List<Long> authors) {
        super(title, price, genre, authors);
        this.isbn = isbn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || BookDto.class != o.getClass()) {
            return false;
        }
        final BookDto that = (BookDto) o;
        return Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(isbn);
    }
}
