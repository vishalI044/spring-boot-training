package com.example.library.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BooksResponse {
    public Long id;
    public String title;
    public String description;
    public String category;
    public Timestamp created_date;
    public Timestamp updated_date;
    public PublishersResponse publisher;
}
