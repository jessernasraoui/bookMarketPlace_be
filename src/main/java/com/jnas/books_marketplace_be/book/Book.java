package com.jnas.books_marketplace_be.book;

import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.order_items.OrderItem;
import com.jnas.books_marketplace_be.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "BOOKS")
public class Book extends AbstractEntity {
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryName category;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User seller;
    @OneToMany(mappedBy = "book")
    private List<OrderItem> orderItems;
    @Column(nullable = false)
    private boolean deleted = false;
   @Column(nullable = false)
    private int quantity ;
}
