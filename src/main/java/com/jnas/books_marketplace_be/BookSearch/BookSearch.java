package com.jnas.books_marketplace_be.BookSearch;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.jnas.books_marketplace_be.book.CategoryName;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "books")
public class BookSearch {

    @Id
    private Long id;

    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private CategoryName category;
    private Long sellerId;
    private boolean deleted = false;
    //private int quantity ;
}

