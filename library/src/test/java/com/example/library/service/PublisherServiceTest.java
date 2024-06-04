package com.example.library.service;

import com.example.library.entity.PublishersResponse;
import com.example.library.entity.tables.Publishers;
import com.example.library.repository.PublishersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublisherServiceTest {
    @InjectMocks
    private PublishersService publishersService;

    @Mock
    private PublishersRepository publishersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPublisher() {
        Publishers publisher = new Publishers();
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");

        when(publishersRepository.save(any(Publishers.class))).thenReturn(publisher);

        ResponseEntity<String> response = publishersService.addPublisher(publisher);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Publisher is register", response.getBody());
    }

    @Test
    void testGetPublishers() {
        Publishers publisher = new Publishers();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setBooks(List.of());
        publisher.setCreated_date(Timestamp.from(Instant.now()));

        when(publishersRepository.findAll()).thenReturn(List.of(publisher));

        ResponseEntity<List<PublishersResponse>> response = publishersService.getPublishers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Publisher", Objects.requireNonNull(response.getBody()).getFirst().getName());
    }

    @Test
    void testGetPublisher() {
        Publishers publisher = new Publishers();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setBooks(List.of());
        publisher.setCreated_date(Timestamp.from(Instant.now()));

        when(publishersRepository.findById(1L)).thenReturn(Optional.of(publisher));

        ResponseEntity<Optional<PublishersResponse>> response = publishersService.getPublisher(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isPresent());
        assertEquals("Test Publisher", response.getBody().get().getName());
    }

    @Test
    void testEditPublisher() {
        Publishers publisher = new Publishers();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");

        when(publishersRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(publishersRepository.save(any(Publishers.class))).thenReturn(publisher);

        Publishers updatedPublisher = new Publishers();
        updatedPublisher.setName("Updated Publisher");
        updatedPublisher.setAddress("Updated Address");

        ResponseEntity<String> response = publishersService.editPublisher(updatedPublisher, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Publisher is updated", response.getBody());
    }

    @Test
    void testDeletePublisher() {
        doNothing().when(publishersRepository).deleteById(1L);

        ResponseEntity<String> response = publishersService.deletePublisher(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("the publisher is deleted", response.getBody());
    }

    @Test
    void testGetPublisherNotFound() {
        when(publishersRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<PublishersResponse>> response = publishersService.getPublisher(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
