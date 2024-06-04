package com.example.library.controller;

import com.example.library.entity.PublishersResponse;
import com.example.library.entity.tables.Publishers;
import com.example.library.service.PublishersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublishersController.class)
public class PublishersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublishersService publishersService;

    @Autowired
    private ObjectMapper objectMapper;

    private Publishers publisher;
    private PublishersResponse publisherResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        publisher = new Publishers();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setCreated_date(Timestamp.from(Instant.now()));

        publisherResponse = new PublishersResponse();
        publisherResponse.setId(publisher.getId());
        publisherResponse.setName(publisher.getName());
        publisherResponse.setAddress(publisher.getAddress());
        publisherResponse.setCreated_date(publisher.getCreated_date());
    }

    @Test
    void testAddPublisher() throws Exception {
        when(publishersService.addPublisher(any(Publishers.class))).thenReturn(ResponseEntity.ok("Test Publisher is register"));

        mockMvc.perform(post("/publishers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Publisher is register"));
    }

    @Test
    void testGetPublishers() throws Exception {
        List<PublishersResponse> publishersResponseList = Collections.singletonList(publisherResponse);
        when(publishersService.getPublishers()).thenReturn(ResponseEntity.ok(publishersResponseList));

        mockMvc.perform(get("/publishers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Publisher"))
                .andExpect(jsonPath("$[0].address").value("Test Address"));
    }

    @Test
    void testGetPublisher() throws Exception {
        when(publishersService.getPublisher(anyLong())).thenReturn(ResponseEntity.ok(Optional.of(publisherResponse)));

        mockMvc.perform(get("/publishers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Publisher"))
                .andExpect(jsonPath("$.address").value("Test Address"));
    }

    @Test
    void testEditPublisher() throws Exception {
        when(publishersService.editPublisher(any(Publishers.class), anyLong())).thenReturn(ResponseEntity.ok("Test Publisher is updated"));

        mockMvc.perform(put("/publishers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Publisher is updated"));
    }

    @Test
    void testDeletePublisher() throws Exception {
        when(publishersService.deletePublisher(anyLong())).thenReturn(ResponseEntity.ok("the publisher is deleted"));

        mockMvc.perform(delete("/publishers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("the publisher is deleted"));
    }
}
