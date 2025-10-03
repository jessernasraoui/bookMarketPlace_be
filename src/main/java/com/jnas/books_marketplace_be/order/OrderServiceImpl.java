package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookRepository;
import com.jnas.books_marketplace_be.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::  mapOrderToOrderResponseDTO)
                .collect(Collectors.toList());
    }

  //  @Override
/*    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {

        Book book= bookService.findBookById(orderRequestDTO.bookId());

        Order order = new Order();
        order.setQuantity(orderRequestDTO.quantity());
        order.setTotalPrice(book.getPrice() * orderRequestDTO.quantity());    }*/
}
