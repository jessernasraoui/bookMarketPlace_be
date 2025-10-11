package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookNotFoundException;
import com.jnas.books_marketplace_be.book.BookRepository;
import com.jnas.books_marketplace_be.cart.CartItemDTO;
import com.jnas.books_marketplace_be.cart.CartService;
import com.jnas.books_marketplace_be.order_items.OrderItem;
import com.jnas.books_marketplace_be.user.User;
import com.jnas.books_marketplace_be.user.UserNotFoundException;
import com.jnas.books_marketplace_be.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDTO addOrder(OrderRequestDTO request) {
        // Get the user
        User user = userRepository.findByIdAndUserIsArchivedFalse(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        //  Get cart items
        List<CartItemDTO> cartItems = cartService.getCart(request.getUserId()).getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        //  Create the Order
        Order order = new Order();
        order.setBuyer(user);
        order.setStatus(OrderStatus.PENDING);

        //  Map cart items to order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            Book book = bookRepository.findById(cartItem.getBookId())
                    .orElseThrow(() -> new BookNotFoundException(cartItem.getBookId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(book);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(book.getPrice()); // price per item
            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        //  Calculate total
        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(total);

        order.setShippingAddress(request.getShippingAddress());
        // Save order and clear cart
        orderRepository.save(order);
        cartService.clearCart(request.getUserId());

        return orderMapper.mapToResponseDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findOrdersByUserIdAndNotArchived(userId, pageable)
                .map(orderMapper::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return orderMapper.mapToResponseDTO(order);
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::mapToResponseDTO);
    }
}
