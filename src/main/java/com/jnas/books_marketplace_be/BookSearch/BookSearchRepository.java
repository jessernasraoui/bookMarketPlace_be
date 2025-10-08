package com.jnas.books_marketplace_be.BookSearch;

import com.jnas.books_marketplace_be.book.CategoryName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookSearchRepository extends ElasticsearchRepository<BookSearch, Long> {

    // 🔍 Free text search (title, author, description)
    Page<BookSearch> findByDeletedFalseAndTitleContainingIgnoreCaseOrDeletedFalseAndAuthorContainingIgnoreCaseOrDeletedFalseAndDescriptionContainingIgnoreCase(
            String title, String author, String description, Pageable pageable);

    //  Filtered search: author + title + category
    Page<BookSearch> findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndCategory(
            String author, String title, CategoryName category, Pageable pageable);

    // 🔎 Filtered search: author + title (no category)
    Page<BookSearch> findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(
            String author, String title, Pageable pageable);

    //  All non-deleted books
    Page<BookSearch> findByDeletedFalse(Pageable pageable);
}
