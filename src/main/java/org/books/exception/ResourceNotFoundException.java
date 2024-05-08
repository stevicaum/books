package org.books.exception;

public class ResourceNotFoundException extends RuntimeException {
  public final static String GENRE = "Genre cannot be found for ID: {}";
  public final static String AUTHORS = "One or more authors from provided list does not exist in database";
  public final static String BOOK = "Book cannot be found for ISBN: {}";
  private static final long serialVersionUID = -1266176142238147750L;

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
