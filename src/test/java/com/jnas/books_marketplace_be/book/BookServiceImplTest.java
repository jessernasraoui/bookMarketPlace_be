package com.jnas.books_marketplace_be.book;

import com.jnas.books_marketplace_be.BookSearch.BookSearchService;
import com.jnas.books_marketplace_be.user.User;
import com.jnas.books_marketplace_be.user.UserNotFoundException;
import com.jnas.books_marketplace_be.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookSearchService bookSearchService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private User testUser;
    private Book testBook;
    private BookRequestDTO testBookRequestDTO;
    private BookResponseDTO testBookResponseDTO;

    @BeforeEach
    void setUp() {
        // Set up test data
        testUser = new User();
        testUser.setId(1L);
        
        testBook = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .price(new BigDecimal("29.99"))
                .description("Test Description")
                .category(CategoryName.FANTASY)
                .seller(testUser)
                .quantity(10)
                .build();
        
        testBookRequestDTO = new BookRequestDTO();
        testBookRequestDTO.setTitle("Test Book");
        testBookRequestDTO.setAuthor("Test Author");
        testBookRequestDTO.setPrice(new BigDecimal("29.99"));
        testBookRequestDTO.setDescription("Test Description");
        testBookRequestDTO.setCategory(CategoryName.FANTASY);
        testBookRequestDTO.setSellerId(1L);
        
        testBookResponseDTO = new BookResponseDTO(
            1L,
            "Test Book",
            "Test Author",
            new BigDecimal("29.99"),
            "Test Description",
            CategoryName.FANTASY
        );
    }

    @Test
    void findBookById_ExistingId_ReturnsBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // Act
        Book result = bookService.findBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        assertEquals(testBook.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findBookById_NonExistingId_ThrowsException() {
        // Arrange
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.findBookById(99L));
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    void addBook_ValidRequest_ReturnsBookResponseDTO() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bookMapper.mapBook(testBookRequestDTO, testUser)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);
        when(bookMapper.toResponseDTO(testBook)).thenReturn(testBookResponseDTO);

        // Act
        BookResponseDTO result = bookService.addBook(testBookRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testBookResponseDTO.getId(), result.getId());
        assertEquals(testBookResponseDTO.getTitle(), result.getTitle());
        verify(userRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(testBook);
        verify(bookSearchService, times(1)).indexBook(testBook);
    }

    @Test
    void addBook_InvalidUserId_ThrowsException() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        testBookRequestDTO.setSellerId(99L);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> bookService.addBook(testBookRequestDTO));
        verify(userRepository, times(1)).findById(99L);
        verify(bookRepository, never()).save(any());
    }
}