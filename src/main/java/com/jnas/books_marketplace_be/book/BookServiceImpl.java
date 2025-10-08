package com.jnas.books_marketplace_be.book;

import com.jnas.books_marketplace_be.BookSearch.BookSearchService;
import com.jnas.books_marketplace_be.user.User;
import com.jnas.books_marketplace_be.user.UserNotFoundException;
import com.jnas.books_marketplace_be.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookSearchService bookSearchService;
    private final BookMapper bookMapper;

    @Override
    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Override
    public BookResponseDTO addBook(BookRequestDTO bookRequest) {
        User user = userRepository.findById(bookRequest.getSellerId())
                .orElseThrow(() -> new UserNotFoundException(bookRequest.getSellerId()));

        Book newBook = bookMapper.mapBook(bookRequest, user);
        Book savedBook = bookRepository.save(newBook);

        // Index in Elasticsearch
        bookSearchService.indexBook(savedBook);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        Book bookToUpdate = findBookById(id);
        bookToUpdate.setTitle(bookRequestDTO.getTitle());
        bookToUpdate.setAuthor(bookRequestDTO.getAuthor());
        bookToUpdate.setPrice(bookRequestDTO.getPrice());
        bookToUpdate.setDescription(bookRequestDTO.getDescription());
        bookToUpdate.setCategory(bookRequestDTO.getCategory());

        Book updatedBook = bookRepository.save(bookToUpdate);

        // Index in Elasticsearch
        bookSearchService.indexBook(updatedBook);

        return bookMapper.toResponseDTO(updatedBook);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        Book book = findBookById(id);
        book.setDeleted(true);
        bookRepository.save(book);

        // Update Elasticsearch index
        bookSearchService.indexBook(book);
    }

    @Override
    public Page<BookResponseDTO> getBooks(String author, String title, CategoryName category, Pageable pageable) {

        author = author != null ? author : "";
        title = title != null ? title : "";

        Page<Book> booksPage;

        if (category != null) {
            booksPage = bookRepository.findByDeletedFalseAndAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndCategory(
                    author, title, category, pageable
            );
        } else {
            booksPage = bookRepository.findByDeletedFalseAndAuthorContainingIgnoreCaseOrDeletedFalseAndTitleContainingIgnoreCase(
                    author, title, pageable
            );
        }

        // Map Book entities to BookResponseDTO
        return booksPage.map(bookMapper::toResponseDTO);
    }

}


