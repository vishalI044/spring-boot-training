package com.example.library.service;

import com.example.library.entity.BooksResponse;
import com.example.library.entity.tables.Publishers;
import com.example.library.entity.PublishersResponse;
import com.example.library.repository.PublishersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PublishersService {
    @Autowired
    PublishersRepository publishersRepository;


    public ResponseEntity<String> addPublisher(Publishers publishersRequest) {
        Publishers publishersDetails = new Publishers();
        publishersDetails.setName(publishersRequest.getName());
        publishersDetails.setAddress(publishersRequest.getAddress());
        publishersDetails.setCreated_date(Timestamp.from(Instant.now()));
        publishersRepository.save(publishersDetails);
        return ResponseEntity.status(HttpStatus.OK).body(publishersRequest.getName() + " is register");
    }

    public ResponseEntity<List<PublishersResponse>> getPublishers() {
        List<Publishers> publishersList = publishersRepository.findAll();
        List<PublishersResponse> publishersResponses = publishersList.stream().map(this::convertToPublishersResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(publishersResponses);
    }

    public ResponseEntity<Optional<PublishersResponse>> getPublisher(Long id) {
        Optional<Publishers> publishersResponse = publishersRepository.findById(id);
        return publishersResponse.map(value -> ResponseEntity.status(HttpStatus.OK).body(Optional.of(convertToPublishersResponse(value))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<String> editPublisher(Publishers publishersRequest, Long id) {
        Publishers publishers = publishersRepository.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found"));
        publishers.setName(publishersRequest.getName());
        publishers.setAddress(publishersRequest.getAddress());
        publishers.setUpdated_date(Timestamp.from(Instant.now()));

        publishersRepository.save(publishers);
        return ResponseEntity.status(HttpStatus.OK).body(publishersRequest.getName() + " is updated");
    }

    public ResponseEntity<String> deletePublisher(Long id) {
        publishersRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("the publisher is deleted");
    }

    private PublishersResponse convertToPublishersResponse(Publishers publishers) {
        PublishersResponse publishersResponse = new PublishersResponse();
        publishersResponse.setName(publishers.getName());
        publishersResponse.setAddress(publishersResponse.getAddress());
        publishersResponse.setId(publishers.getId());
        publishersResponse.setCreated_date(publishers.getCreated_date());
        publishersResponse.setUpdated_date(publishers.getUpdated_date());

        List<BooksResponse> booksResponses = publishers.getBooks().stream().map(book -> {
            BooksResponse bookDetails = new BooksResponse();
            bookDetails.setId(book.getId());
            bookDetails.setTitle(book.getTitle());
            bookDetails.setDescription(book.getDescription());
            bookDetails.setCategory(book.getCategory());
            bookDetails.setCreated_date(book.getCreated_date());
            bookDetails.setUpdated_date(book.getUpdated_date());
            return bookDetails;
        }).toList();

        publishersResponse.setBooks(booksResponses);

        return publishersResponse;
    }
}
