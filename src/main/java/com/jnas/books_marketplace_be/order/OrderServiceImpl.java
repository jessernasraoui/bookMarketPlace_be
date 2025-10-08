package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookRepository;
import com.jnas.books_marketplace_be.book.BookService;
import com.jnas.books_marketplace_be.cart.CartItemDTO;
import com.jnas.books_marketplace_be.cart.CartService;
import com.jnas.books_marketplace_be.order_items.OrderItem;
import com.jnas.books_marketplace_be.user.User;
import com.jnas.books_marketplace_be.user.UserRepository;
import lombok.AllArgsConstructor;
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
        // 1️⃣ Get the user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Get cart items
        List<CartItemDTO> cartItems = cartService.getCart(request.getUserId()).getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3️⃣ Create the Order
        Order order = new Order();
        order.setBuyer(user);
        order.setStatus(OrderStatus.PENDING);

        // 4️⃣ Map cart items to order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            Book book = bookRepository.findById(cartItem.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(book);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(book.getPrice()); // price per item
            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // 5️⃣ Calculate total
        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(total);

        // 6️⃣ Save order and clear cart
        orderRepository.save(order);
        cartService.clearCart(request.getUserId());

        // 7️⃣ Return response
        return orderMapper.mapToResponseDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findByBuyerId(userId, pageable)
                .map(orderMapper::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.mapToResponseDTO(order);
    }
}
