package com.jnas.books_marketplace_be.cart;

import java.util.List;

public interface CartService {
     String getCartKey(Long userId);
    void addItemToCart(Long userId, Long bookId, int quantity);
    void removeItemFromCart(Long userId, Long bookId);
    void updateCartItemQuantity(Long userId, Long bookId, int quantity);
    CartDTO getCart(Long userId);
    void clearCart(Long userId);

    List<CartItemDTO> getCartItemsByUser(Long userId);
}
