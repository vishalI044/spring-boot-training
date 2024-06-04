package com.example.library.entity.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "publishers")
@Entity
@Getter
@Setter
public class Publishers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public String address;
    public Timestamp created_date;
    public Timestamp updated_date;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Books> books;
}
