package com.jnas.books_marketplace_be.order_items;
import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.order.Order;
import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "ORDER_ITEMS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    private int quantity;
    private BigDecimal price;

}
