package com.jnas.books_marketplace_be.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("User with ID " + id + " not found");
    }
}
