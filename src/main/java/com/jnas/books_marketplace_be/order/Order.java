package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.order_items.OrderItem;
import com.jnas.books_marketplace_be.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity {
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(nullable = false)
    private int quantity; // number of copies available in stock
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User buyer;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    private String shippingAddress;

}
