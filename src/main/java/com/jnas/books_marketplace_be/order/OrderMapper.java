package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.order_items.OrderItemsResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public  class OrderMapper {

    public  OrderResponseDTO mapToResponseDTO(Order order) {
        List<OrderItemsResponseDTO> items = order.getOrderItems().stream()
                .map(item -> {
                    OrderItemsResponseDTO dto = new OrderItemsResponseDTO();
                    dto.setBookId(item.getBook().getId());
                    dto.setTitle(item.getBook().getTitle());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    return dto;
                })
                .collect(Collectors.toList());

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getBuyer().getId());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setItems(items);
        dto.setTotal(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
