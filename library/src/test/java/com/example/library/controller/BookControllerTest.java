package com.example.library.controller;

import com.example.library.entity.PublishersResponse;
import com.example.library.entity.tables.Books;
import com.example.library.entity.BooksResponse;
import com.example.library.entity.tables.Publishers;
import com.example.library.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @Autowired
    private ObjectMapper objectMapper;

    private Books book;
    private BooksResponse bookResponse;

    @BeforeEach
    void setUp() {
        Publishers publisher = new Publishers();
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setCreated_date(Timestamp.from(Instant.now()));

        book = new Books();
        book.setTitle("Test Book");
        book.setDescription("Test Description");
        book.setCategory("Test Category");
        book.setCreated_date(Timestamp.from(Instant.now()));
        book.setPublisher(publisher);

        bookResponse = new BooksResponse();
        bookResponse.setId(1L);
        bookResponse.setTitle("Test Book");
        bookResponse.setDescription("Test Description");
        bookResponse.setCategory("Test Category");
        bookResponse.setPublisher(new PublishersResponse(publisher.getId(), publisher.getName(), publisher.getAddress(), publisher.getCreated_date(), publisher.getUpdated_date()));
    }

    @Test
    void testAddBook() throws Exception {
        when(booksService.addBook(any(Books.class))).thenReturn(ResponseEntity.ok("Test Book book is add"));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Book book is add"));
    }

    @Test
    void testGetBooks() throws Exception {
        List<BooksResponse> booksResponseList = Collections.singletonList(bookResponse);
        when(booksService.getBooks()).thenReturn(ResponseEntity.ok(booksResponseList));

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].category").value("Test Category"));
    }

    @Test
    void testGetBook() throws Exception {
        when(booksService.getBook(anyLong())).thenReturn(ResponseEntity.ok(Optional.of(bookResponse)));

        mockMvc.perform(get("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.category").value("Test Category"));
    }

    @Test
    void testEditBook() throws Exception {
        when(booksService.editBook(any(Books.class), anyLong())).thenReturn(ResponseEntity.ok("Test Book is updated"));

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Book is updated"));
    }

    @Test
    void testDeleteBook() throws Exception {
        when(booksService.deleteBook(anyLong())).thenReturn(ResponseEntity.ok("the book is deleted"));

        mockMvc.perform(delete("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("the book is deleted"));
    }
}
