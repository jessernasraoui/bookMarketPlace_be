package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.order_details.OrderDetailsDTO;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        String buyerName,
        BigDecimal price,
        List<OrderDetailsDTO> orderDetails
) {}