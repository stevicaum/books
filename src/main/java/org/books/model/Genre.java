package org.books.model;

import jakarta.persistence.*;

@Entity
@Table ( name = "genre" )
public class Genre {

    @SequenceGenerator ( name = "seq_genre", sequenceName = "seq_genre", allocationSize = 1 )
    @Id
    @GeneratedValue ( generator = "seq_genre" )
    private Long id;
    private String name;

    protected Genre() {}

    public Genre( final Long id, final String name ) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Genre genre = (Genre) o;

        if ( !id.equals( genre.id ) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
