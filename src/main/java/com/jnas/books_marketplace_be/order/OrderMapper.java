package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.order_details.OrderDetailsDTO;
import com.jnas.books_marketplace_be.order_details.OrderDetailsMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDTO mapOrderToOrderResponseDTO(Order order) {

        List<OrderDetailsDTO> detailsDTOs = order.getOrderDetails().stream()
                .map(OrderDetailsMapper::mapOrderDetailsToOrderDetailsDTo)
                .collect(Collectors.toList());
        return new OrderResponseDTO(
                order.getId(),
                order.getBuyer().getUsername(),
                order.getPrice(),
                detailsDTOs
        );

    }
}
