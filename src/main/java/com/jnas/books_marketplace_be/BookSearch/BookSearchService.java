package com.jnas.books_marketplace_be.BookSearch;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.CategoryName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookSearchService {
    void indexBook(Book book);
    Page <BookSearch> searchBooks(String query, String author, String title, CategoryName category, Pageable pageable);
}
