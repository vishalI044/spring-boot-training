package com.example.library.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PublishersResponse {
    public Long id;
    public String name;
    public String address;
    public Timestamp created_date;
    public Timestamp updated_date;
    public List<BooksResponse> books;

    public PublishersResponse(Long id, String name, String address, Timestamp createdDate, Timestamp updatedDate) {
    }
}
