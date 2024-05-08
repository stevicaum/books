package org.books.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class BookNoIsbnDto {
    @NotBlank(message = "This field can't be empty")
    private String title;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2)
    private BigDecimal price;
    @Min(1)
    private Long genreId;
    @Size(min = 1)
    private List<Long> authorIds;

    protected BookNoIsbnDto() {

    }

    public BookNoIsbnDto(final String title, final BigDecimal price, final Long genreId,
                         final List<Long> authorIds) {
        this.title = title;
        this.price = price;
        this.genreId = genreId;
        this.authorIds = authorIds;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getGenreId() {
        return genreId;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }
}
