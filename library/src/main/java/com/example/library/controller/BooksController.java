package com.example.library.controller;

import com.example.library.entity.tables.Books;
import com.example.library.entity.BooksResponse;
import com.example.library.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BooksController {
    @Autowired
    BooksService booksService;

    @PostMapping()
    public ResponseEntity<String> addBook(@RequestBody Books booksRequest) {
        return booksService.addBook(booksRequest);
    }

    @GetMapping()
    public ResponseEntity<List<BooksResponse>> getBooks() {
        return booksService.getBooks();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<BooksResponse>> getBook(@PathVariable Long id) {
        return booksService.getBook(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> editBook(@RequestBody Books booksRequest, @PathVariable Long id) {
        return booksService.editBook(booksRequest, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return booksService.deleteBook(id);
    }
}
