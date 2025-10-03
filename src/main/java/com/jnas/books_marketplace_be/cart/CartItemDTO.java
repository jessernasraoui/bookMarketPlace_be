package com.jnas.books_marketplace_be.cart;

public record CartItemDTO(
        Long bookId,
        String bookTitle,
        int quantity,
        double totalPrice
) {
}
