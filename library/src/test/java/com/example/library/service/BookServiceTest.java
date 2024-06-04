package com.example.library.service;

import com.example.library.entity.BooksResponse;
import com.example.library.entity.tables.Books;
import com.example.library.entity.tables.Publishers;
import com.example.library.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    private Books book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    }

    @Test
    void testAddBook() {
        when(booksRepository.save(any(Books.class))).thenReturn(book);

        ResponseEntity<String> response = booksService.addBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Book book is add", response.getBody());
        verify(booksRepository, times(1)).save(any(Books.class));
    }

    @Test
    void testGetBooks() {
        when(booksRepository.findAll()).thenReturn(Collections.singletonList(book));

        ResponseEntity<List<BooksResponse>> response = booksService.getBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals("Test Book", response.getBody().getFirst().getTitle());
        verify(booksRepository, times(1)).findAll();
    }

    @Test
    void testGetBook() {
        when(booksRepository.findById(anyLong())).thenReturn(Optional.of(book));

        ResponseEntity<Optional<BooksResponse>> response = booksService.getBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isPresent());
        verify(booksRepository, times(1)).findById(anyLong());
    }

    @Test
    void testEditBook() {
        when(booksRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(booksRepository.save(any(Books.class))).thenReturn(book);

        book.setTitle("Updated Book");
        ResponseEntity<String> response = booksService.editBook(book, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Book is updated", response.getBody());
        verify(booksRepository, times(1)).findById(anyLong());
        verify(booksRepository, times(1)).save(any(Books.class));
    }

    @Test
    void testDeleteBook() {
        doNothing().when(booksRepository).deleteById(anyLong());

        ResponseEntity<String> response = booksService.deleteBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("the book is deleted", response.getBody());
        verify(booksRepository, times(1)).deleteById(anyLong());
    }
}
