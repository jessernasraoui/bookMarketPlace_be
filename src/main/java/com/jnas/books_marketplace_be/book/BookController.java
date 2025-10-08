package com.jnas.books_marketplace_be.book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getBooks(@PageableDefault(size = 10, sort = "title") Pageable pageable, @RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) CategoryName category) {
        Page<BookResponseDTO> book = bookService.getBooks(author, title, category, pageable);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequest) {
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

}
