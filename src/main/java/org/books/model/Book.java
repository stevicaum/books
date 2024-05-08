package org.books.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table ( name = "book" )
public class Book {

    @Id
    private String isbn;
    private String title;
    @Column ( name = "price" )
    private BigDecimal price;

    @ManyToMany ( cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY )
    @JoinTable ( name = "intellectual_property", joinColumns = @JoinColumn ( name = "book_isbn" ),
        inverseJoinColumns = @JoinColumn ( name = "author_id" ) )
    private List<Author> authors = new ArrayList<>();

    @OneToOne ( fetch = FetchType.LAZY )
    @JoinColumn ( name = "genre_id" )
    private Genre genre;

    protected Book() {

    }

    public Book(String isbn, String title, BigDecimal price, List<Author> authors, Genre genre ) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.authors = authors;
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Book book = (Book) o;

        if ( !isbn.equals( book.isbn ) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
