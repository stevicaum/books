package org.books.repository;

import org.books.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, String>, CrudRepository<Book, String> {
    @Query( "select bb from Book bb where bb.isbn in (select distinct b.isbn from Book b left join b.authors a " +
            "left join b.genre g where lower(b.title) like lower(concat('%',:title,'%')) or lower(a.firstName)=lower(:author) " +
            "or lower(a.lastName)=lower(:author) or lower(g.name)=lower(:genre))" )
    Page<Book> findBooksBy(@Param( "title" ) String title, @Param( "author" ) String author, @Param( "genre" ) String genre, Pageable pageable);
}
