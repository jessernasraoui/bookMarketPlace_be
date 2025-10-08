package com.jnas.books_marketplace_be.order_items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsResponseDTO {
    private Long bookId;
    private String title;
    private int quantity;
    private BigDecimal price;
}
