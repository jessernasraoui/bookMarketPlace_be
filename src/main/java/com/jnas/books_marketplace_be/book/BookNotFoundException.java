package com.jnas.books_marketplace_be.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book with ID " + id + " not found");
    }
}
