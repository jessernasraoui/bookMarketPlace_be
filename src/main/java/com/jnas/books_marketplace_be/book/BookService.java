package com.jnas.books_marketplace_be.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book findBookById(Long id);

    BookResponseDTO addBook(BookRequestDTO book);

    BookResponseDTO updateBook(Long id, BookRequestDTO book);

    void deleteBook(Long id);

    Page<BookResponseDTO> getBooks(String author, String title, CategoryName category, Pageable pageable);
}
