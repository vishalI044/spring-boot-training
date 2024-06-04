package com.example.library.entity.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "books")
@Entity
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String title;
    public String description;
    public String category;
    public Timestamp created_date;
    public Timestamp updated_date;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    public Publishers publisher;
}
