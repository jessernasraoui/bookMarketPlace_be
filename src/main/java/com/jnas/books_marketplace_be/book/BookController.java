package com.jnas.books_marketplace_be.book;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getBooks(@PageableDefault( sort = "title") Pageable pageable, @RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) CategoryName category) {
        Page<BookResponseDTO> book = bookService.getBooks(author, title, category, pageable);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequest) {
        BookResponseDTO book = bookService.addBook(bookRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();
        return ResponseEntity.created(location).body(book);    }

}
