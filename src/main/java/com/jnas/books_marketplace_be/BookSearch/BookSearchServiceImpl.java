package com.jnas.books_marketplace_be.BookSearch;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.book.BookRepository;
import com.jnas.books_marketplace_be.book.CategoryName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {

private final BookSearchRepository bookSearchRepository;

    @Override
    public void indexBook(Book book) {
        BookSearch doc = BookSearch.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .category(book.getCategory())
                .sellerId(book.getSeller().getId())
                .deleted(book.isDeleted())
                .build();

        bookSearchRepository.save(doc);
    }

    @Override
    public Page<BookSearch> searchBooks(String query, String author, String title, CategoryName category, Pageable pageable) {

        //  If user typed a search query (free text)
        if (query != null && !query.isEmpty()) {
            return bookSearchRepository
                    .findByDeletedFalseAndTitleContainingIgnoreCaseOrDeletedFalseAndAuthorContainingIgnoreCaseOrDeletedFalseAndDescriptionContainingIgnoreCase(
                            query, query, query, pageable
                    );
        }

        // 🧾 If no filters are provided
        if ((author == null || author.isEmpty()) && (title == null || title.isEmpty()) && category == null) {
            return bookSearchRepository.findByDeletedFalse(pageable);
        }

        // 🎯 Filtered search
        if (category != null) {
            return bookSearchRepository.findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndCategory(
                    author != null ? author : "",
                    title != null ? title : "",
                    category,
                    pageable
            );
        } else {
            return bookSearchRepository.findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(
                    author != null ? author : "",
                    title != null ? title : "",
                    pageable
            );
        }
    }
}
