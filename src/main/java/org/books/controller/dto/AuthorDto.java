package org.books.controller.dto;

import org.books.model.Author;

public class AuthorDto {

    private Long id;
    private String name;

    protected AuthorDto() {

    }

    public AuthorDto(final Long id, final String name ) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static class AuthorViewBuilder {

        public static final String DELIMITER = " ";
        private final Author author;

        public AuthorViewBuilder( final Author author ) {
            this.author = author;
        }

        public AuthorDto build() {
            return new AuthorDto( author.getId(), String.join( DELIMITER, author.getFirstName(), author
                    .getLastName() ) );
        }
    }
}
