package com.example.library.controller;

import com.example.library.entity.tables.Publishers;
import com.example.library.entity.PublishersResponse;
import com.example.library.service.PublishersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PublishersController {
    @Autowired
    PublishersService publishersService;

    @PostMapping()
    public ResponseEntity<String> addPublisher(@RequestBody Publishers publishersRequest) {
        return publishersService.addPublisher(publishersRequest);
    }

    @GetMapping()
    public ResponseEntity<List<PublishersResponse>> getPublishers() {
        return publishersService.getPublishers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PublishersResponse>> getPublisher(@PathVariable Long id) {
        return publishersService.getPublisher(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> editPublisher(@RequestBody Publishers publishersRequest, @PathVariable Long id) {
        return publishersService.editPublisher(publishersRequest, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable Long id) {
        return publishersService.deletePublisher(id);
    }
}
