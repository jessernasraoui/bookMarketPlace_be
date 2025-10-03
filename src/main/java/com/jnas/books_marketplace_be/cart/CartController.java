package com.jnas.books_marketplace_be.cart;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        CartDTO cart = cartService.getCart(userId);
        return (cart == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/items/{bookId}")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long userId,
                                                @PathVariable Long bookId,
                                                @RequestParam  int quantity) {
         cartService.addItemToCart(userId, bookId, quantity) ;
        return ResponseEntity.ok(cartService.getCart(userId));

    }

    @PutMapping("/{userId}/items/{bookId}")
    public ResponseEntity<CartDTO> updateCartItemQuantity(@PathVariable Long userId,
                                                          @PathVariable Long bookId,
                                                          @RequestParam int newQuantity) {
        cartService.updateCartItemQuantity(userId, bookId, newQuantity);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // Remove an item from the cart
    @DeleteMapping("/{userId}/items/{bookId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long userId,
                                                      @PathVariable Long bookId){
        cartService.removeItemFromCart(userId, bookId);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    //  Clear the entire cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
