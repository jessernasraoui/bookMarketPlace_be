package com.jnas.books_marketplace_be.book;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookRequestDTO {
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private CategoryName category;
    private Long sellerId;
}
