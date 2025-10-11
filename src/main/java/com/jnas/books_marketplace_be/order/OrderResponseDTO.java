package com.jnas.books_marketplace_be.order;


import com.jnas.books_marketplace_be.order_items.OrderItemsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private List<OrderItemsResponseDTO> items;
    private BigDecimal total;
    private OrderStatus status;
    private String shippingAddress;
}