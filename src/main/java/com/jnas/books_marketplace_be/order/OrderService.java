package com.jnas.books_marketplace_be.order;

import java.util.List;

public interface OrderService {

List<OrderResponseDTO> getAllOrders();
//OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO);

}
