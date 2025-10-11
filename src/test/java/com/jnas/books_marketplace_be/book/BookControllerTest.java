package com.jnas.books_marketplace_be.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private BookRequestDTO bookRequestDTO;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("Test Book");
        bookRequestDTO.setAuthor("Test Author");
        bookRequestDTO.setPrice(new BigDecimal("29.99"));
        bookRequestDTO.setDescription("Test Description");
        bookRequestDTO.setCategory(CategoryName.FANTASY);
        bookRequestDTO.setSellerId(1L);

        bookResponseDTO = new BookResponseDTO(
            1L,
            "Test Book",
            "Test Author",
            new BigDecimal("29.99"),
            "Test Description",
            CategoryName.FANTASY
        );
    }

    @Test
    void getBooks_ReturnsBookPage() throws Exception {
        // Arrange
        when(bookService.getBooks(any(), any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(bookResponseDTO)));

        // Act & Assert
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Test Book"))
                .andExpect(jsonPath("$.content[0].author").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    void createBook_ValidRequest_ReturnsCreated() throws Exception {
        // Arrange
        when(bookService.addBook(any(BookRequestDTO.class))).thenReturn(bookResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }

    @Test
    void createBook_Unauthorized_ReturnsUnauthorized() throws Exception {
        // Act & Assert - without @WithMockUser
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isUnauthorized());
    }
}