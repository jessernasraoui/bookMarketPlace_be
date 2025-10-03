package com.jnas.books_marketplace_be.cart;

import java.util.List;

public record CartDTO(
        Long userId,
        List<CartItemDTO> items,
        double totalPrice
) {}
