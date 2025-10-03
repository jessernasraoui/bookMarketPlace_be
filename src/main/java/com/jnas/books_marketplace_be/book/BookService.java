package com.jnas.books_marketplace_be.book;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Book findBookById(long bookId);
}
