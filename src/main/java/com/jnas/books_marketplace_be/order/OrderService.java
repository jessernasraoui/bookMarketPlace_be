package com.jnas.books_marketplace_be.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    // Create order from cart
    OrderResponseDTO addOrder(OrderRequestDTO request);

    // Get all user orders
    Page<OrderResponseDTO> getOrdersByUser(Long userId, Pageable pageable);

    // Get single order
    OrderResponseDTO getOrderById(Long orderId);

    Page<OrderResponseDTO> getAllOrders(Pageable pageable);




}
