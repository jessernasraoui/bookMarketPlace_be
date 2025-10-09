package com.jnas.books_marketplace_be.cart;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookNotFoundException;
import com.jnas.books_marketplace_be.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final BookRepository bookRepository;

    @Override
    public String getCartKey(Long userId) {
        return "user:" + userId + ":cart";
    }

    @Override
    public void addItemToCart(Long userId, Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        CartDTO cart = getCart(userId);
        List<CartItemDTO> items = (cart != null) ? new ArrayList<>(cart.getItems()) : new ArrayList<>();

        Optional<CartItemDTO> existingItem = items.stream()
                .filter(i -> i.getBookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemDTO oldItem = existingItem.get();
            items.remove(oldItem);
            int newQty = oldItem.getQuantity() + quantity;
            items.add(new CartItemDTO(bookId, book.getTitle(), newQty,  book.getPrice().multiply(BigDecimal.valueOf(newQty))));
        } else {
            items.add(new CartItemDTO(bookId, book.getTitle(), quantity, book.getPrice().multiply(BigDecimal.valueOf(quantity))));
        }

        BigDecimal totalPrice = items.stream()
                .map(CartItemDTO::getTotalPrice) // returns BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CartDTO updatedCart = new CartDTO(userId, items, totalPrice);

        redisTemplate.opsForValue().set(getCartKey(userId), updatedCart);
    }

    @Override
    public void removeItemFromCart(Long userId, Long bookId) {
        CartDTO cart = getCart(userId);
        if (cart == null) return;

        List<CartItemDTO> items = cart.getItems().stream()
                .filter(i -> !i.getBookId().equals(bookId))
                .toList();

        BigDecimal totalPrice = items.stream().map(CartItemDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        CartDTO updatedCart = new CartDTO(userId, new ArrayList<>(items), totalPrice);

        redisTemplate.opsForValue().set(getCartKey(userId), updatedCart);
    }

    @Override
    public void updateCartItemQuantity(Long userId, Long bookId, int newQuantity) {
        CartDTO cart = getCart(userId);
        if (cart == null) return;

        List<CartItemDTO> items = new ArrayList<>(cart.getItems());
        items.removeIf(i -> i.getBookId().equals(bookId));

        if (newQuantity > 0) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException(bookId));
            items.add(new CartItemDTO(bookId, book.getTitle(), newQuantity, book.getPrice().multiply(BigDecimal.valueOf(newQuantity))));
        }

        BigDecimal totalPrice = items.stream().map(CartItemDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        CartDTO updatedCart = new CartDTO(userId, items, totalPrice);

        redisTemplate.opsForValue().set(getCartKey(userId), updatedCart);
    }

    @Override
    public CartDTO getCart(Long userId) {
        return (CartDTO) redisTemplate.opsForValue().get(getCartKey(userId));
    }

    @Override
    public void clearCart(Long userId) {
        redisTemplate.delete(getCartKey(userId));
    }

    @Override
    public List<CartItemDTO> getCartItemsByUser(Long userId) {
        return List.of();
    }
}
