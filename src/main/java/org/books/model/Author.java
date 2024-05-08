package org.books.model;

import jakarta.persistence.*;


@Entity
@Table( name = "author" )
public class Author {

    @SequenceGenerator( name = "seq_author", sequenceName = "seq_author", allocationSize = 1 )
    @Id
    @GeneratedValue( generator = "seq_author" )
    private Long id;
    @Column ( name = "first_name" )
    private String firstName;
    @Column ( name = "last_name" )
    private String lastName;

    protected Author() {}

    public Author( final Long id, final String firstName, final String lastName ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Author author = (Author) o;

        if ( !id.equals( author.id ) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
