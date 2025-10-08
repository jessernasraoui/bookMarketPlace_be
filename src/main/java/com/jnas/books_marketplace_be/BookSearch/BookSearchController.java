package com.jnas.books_marketplace_be.BookSearch;

import com.jnas.books_marketplace_be.book.CategoryName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookSearch")
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @GetMapping
    public Page<BookSearch> searchBooks(@RequestParam(required = false) String query,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) String author,
                                        @RequestParam(required = false) CategoryName category,
                                      @PageableDefault(sort = "title") Pageable pageable) {
        return bookSearchService.searchBooks(query, author, title, category, pageable);
    }
}
