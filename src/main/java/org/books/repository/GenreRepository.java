package org.books.repository;


import org.books.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>, JpaRepository<Genre, Long> {

}
