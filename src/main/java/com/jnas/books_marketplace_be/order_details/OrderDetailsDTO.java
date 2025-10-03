package com.jnas.books_marketplace_be.order_details;

import java.math.BigDecimal;

public record OrderDetailsDTO(
        String bookTitle,
        int quantity,
        BigDecimal totalPrice
) {}
