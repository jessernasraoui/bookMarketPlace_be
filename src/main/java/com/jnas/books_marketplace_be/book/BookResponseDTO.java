package com.jnas.books_marketplace_be.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BookResponseDTO {
    private final Long id;
    private final String title;
    private final String author;
    private final BigDecimal price;
    private final String description;
    private final CategoryName category;
}
