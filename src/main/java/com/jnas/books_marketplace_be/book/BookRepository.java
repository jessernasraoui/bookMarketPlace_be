package com.jnas.books_marketplace_be.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Search by author or title, only active books
    Page<Book> findByDeletedFalseAndAuthorContainingIgnoreCaseOrDeletedFalseAndTitleContainingIgnoreCase(
            String author, String title, Pageable pageable);

    // Search by author, title, and category, only active books
    Page<Book> findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndCategory(
            String author, String title, CategoryName category, Pageable pageable);
}
