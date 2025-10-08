package com.jnas.books_marketplace_be.book;

import com.jnas.books_marketplace_be.user.User;
import com.jnas.books_marketplace_be.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private UserService userService;

    public Book mapBook(BookRequestDTO bookRequestDTO, User user) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setDescription(bookRequestDTO.getDescription());
        book.setCategory(bookRequestDTO.getCategory());
        book.setSeller(user);
        return book;
    }

    public BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getDescription(),
                book.getCategory()
        );
    }
}
