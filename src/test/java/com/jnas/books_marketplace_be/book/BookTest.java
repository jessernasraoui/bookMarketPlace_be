package com.jnas.books_marketplace_be.book;

import com.jnas.books_marketplace_be.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation() {
        // Arrange
        User seller = new User();
        seller.setId(1L);
        
        // Act
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .price(new BigDecimal("29.99"))
                .description("Test Description")
                .category(CategoryName.FANTASY)
                .seller(seller)
                .quantity(10)
                .build();
        
        // Assert
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(new BigDecimal("29.99"), book.getPrice());
        assertEquals("Test Description", book.getDescription());
        assertEquals(CategoryName.FANTASY, book.getCategory());
        assertEquals(seller, book.getSeller());
        assertEquals(10, book.getQuantity());
        assertFalse(book.isDeleted());
    }
    
    @Test
    void testBookEquals() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        
        Book book2 = new Book();
        book2.setId(1L);
        
        Book book3 = new Book();
        book3.setId(2L);
        
        // Assert - books with same ID should be equal
        assertEquals(book1, book2);
        
        // Assert - books with different IDs should not be equal
        assertNotEquals(book1, book3);
    }
}