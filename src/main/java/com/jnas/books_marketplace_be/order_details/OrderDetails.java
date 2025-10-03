package com.jnas.books_marketplace_be.order_details;
import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.order.Order;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "ORDER_DETAILS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    private int quantity;
    private BigDecimal totalPrice;

}
