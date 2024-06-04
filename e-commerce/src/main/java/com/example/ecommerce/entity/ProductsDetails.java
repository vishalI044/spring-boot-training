package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "products_details")
public class ProductsDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public Double price;
    public Double quantity;
    public String status;
    public String description;
    public String category;
    public Timestamp created_at;
    public Timestamp updated_at;
}
