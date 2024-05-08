DROP TABLE IF EXISTS intellectual_property;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP SEQUENCE IF EXISTS seq_genre;
DROP SEQUENCE IF EXISTS seq_author;

CREATE TABLE book
(
  isbn VARCHAR(25) PRIMARY KEY NOT NULL,
  title VARCHAR(255),
  price DECIMAL(6,2),
  genre_id BIGINT
);

CREATE SEQUENCE seq_genre START 1 INCREMENT 1;
CREATE TABLE genre
(
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(255)
);

CREATE SEQUENCE seq_author START 1 INCREMENT 1;
CREATE TABLE author
(
  id BIGINT PRIMARY KEY NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255)
);

CREATE TABLE intellectual_property
(
  author_id BIGINT NOT NULL REFERENCES author(id),
  book_isbn VARCHAR(25) NOT NULL REFERENCES book(isbn),
  PRIMARY KEY (author_id,book_isbn)
);