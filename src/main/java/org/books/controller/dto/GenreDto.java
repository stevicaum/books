package org.books.controller.dto;


import org.books.model.Genre;

public class GenreDto {

    private Long id;
    private String genreName;

    protected GenreDto() {

    }

    public GenreDto(final Long id, final String genreName ) {
        this.id = id;
        this.genreName = genreName;
    }

    public Long getId() {
        return id;
    }

    public String getGenreName() {
        return genreName;
    }

    public static class GenreViewBuilder {

        private final Genre genre;

        public GenreViewBuilder( final Genre genre ) {
            this.genre = genre;
        }

        public GenreDto build() {
            return new GenreDto( genre.getId(), genre.getName() );
        }
    }
}
