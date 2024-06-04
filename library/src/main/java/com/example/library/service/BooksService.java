package com.example.library.service;

import com.example.library.entity.tables.Books;
import com.example.library.entity.BooksResponse;
import com.example.library.entity.PublishersResponse;
import com.example.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    @Autowired
    BooksRepository booksRepository;

    public ResponseEntity<String> addBook(Books booksRequest) {
        Books booksDetails = new Books();

        booksDetails.setPublisher(booksRequest.getPublisher());
        booksDetails.setTitle(booksRequest.getTitle());
        booksDetails.setCategory(booksRequest.getCategory());
        booksDetails.setDescription(booksRequest.getDescription());
        booksDetails.setCreated_date(Timestamp.from(Instant.now()));

        booksRepository.save(booksDetails);
        return ResponseEntity.status(HttpStatus.OK).body(booksDetails.getTitle() + " book is add");
    }

    public ResponseEntity<List<BooksResponse>> getBooks() {
        List<Books> booksDetails = booksRepository.findAll();
        List<BooksResponse> booksResponse = booksDetails.stream().map(this::convertToBookResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(booksResponse);
    }

    public ResponseEntity<Optional<BooksResponse>> getBook(Long id) {
        Optional<Books> bookDetails = booksRepository.findById(id);
        return bookDetails.map(value -> ResponseEntity.status(HttpStatus.OK).body(Optional.of(convertToBookResponse(value))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<String> editBook(Books booksRequest, Long id) {
        Books booksDetails = booksRepository.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found"));
        booksDetails.setTitle(booksRequest.getTitle());
        booksDetails.setCategory(booksRequest.getCategory());
        booksDetails.setDescription(booksRequest.getDescription());
        booksDetails.setUpdated_date(Timestamp.from(Instant.now()));

        booksRepository.save(booksDetails);
        return ResponseEntity.status(HttpStatus.OK).body(booksRequest.getTitle() + " is updated");
    }

    public ResponseEntity<String> deleteBook(Long id) {
        booksRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("the book is deleted");
    }

    private BooksResponse convertToBookResponse(Books book){
        BooksResponse booksResponse = new BooksResponse();
        booksResponse.setId(book.getId());
        booksResponse.setTitle(book.getTitle());
        booksResponse.setDescription(book.getDescription());
        booksResponse.setCategory(book.getCategory());

        PublishersResponse publishersResponse = new PublishersResponse();
        publishersResponse.setId(book.getPublisher().getId());
        publishersResponse.setName(book.getPublisher().getName());
        publishersResponse.setAddress(book.getPublisher().getAddress());
        publishersResponse.setCreated_date(book.getPublisher().getCreated_date());
        publishersResponse.setUpdated_date(book.getPublisher().getUpdated_date());

        booksResponse.setPublisher(publishersResponse);

        return booksResponse;
    }
}
