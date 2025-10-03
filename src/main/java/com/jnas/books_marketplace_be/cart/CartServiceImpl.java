package com.jnas.books_marketplace_be.cart;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookNotFoundException;
import com.jnas.books_marketplace_be.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        // check if user's cart is empty or not
        List<CartItemDTO> items = (cart != null) ? new ArrayList<>(cart.items()) : new ArrayList<>();

        // Check if item already exists
        Optional<CartItemDTO> existingItem = items.stream()
                .filter(i -> i.bookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemDTO oldItem = existingItem.get();
            items.remove(oldItem);
            int newQty = oldItem.quantity() + quantity;
            items.add(new CartItemDTO(bookId, book.getTitle(), newQty, newQty * book.getPrice()));
        } else {
            items.add(new CartItemDTO(bookId, book.getTitle(), quantity, quantity * book.getPrice()));
        }

        double totalPrice = items.stream().mapToDouble(CartItemDTO::totalPrice).sum();
        CartDTO updatedCart = new CartDTO(userId, items, totalPrice);

        redisTemplate.opsForValue().set(getCartKey(userId), updatedCart);
    }


    @Override
    public void removeItemFromCart(Long userId, Long bookId) {
        CartDTO cart = getCart(userId);
        if (cart == null) return;

        List<CartItemDTO> items = cart.items().stream()
                .filter(i -> !i.bookId().equals(bookId))
                .toList();

        double totalPrice = items.stream().mapToDouble(CartItemDTO::totalPrice).sum();
        CartDTO updatedCart = new CartDTO(userId, items, totalPrice);

        redisTemplate.opsForValue().set(getCartKey(userId), updatedCart);
    }

    // update quantity of a cart item
    @Override
    public void updateCartItemQuantity(Long userId, Long bookId, int newQuantity) {
        CartDTO cart = getCart(userId);
        if (cart == null) return;

        List<CartItemDTO> items = new ArrayList<>(cart.items());
        items.removeIf(i -> i.bookId().equals(bookId));

        if (newQuantity > 0) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException(bookId));
            items.add(new CartItemDTO(bookId, book.getTitle(), newQuantity, newQuantity * book.getPrice()));
        }

        double totalPrice = items.stream().mapToDouble(CartItemDTO::totalPrice).sum();
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
}
