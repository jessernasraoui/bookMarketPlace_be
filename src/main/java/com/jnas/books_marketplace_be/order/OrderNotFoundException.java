package com.jnas.books_marketplace_be.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long id) {
        super("Order with ID " + id + " is not found");
    }
}
