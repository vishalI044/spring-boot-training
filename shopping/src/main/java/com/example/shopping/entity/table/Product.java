package com.example.shopping.entity.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", unique = true)
    public String name;

    @Column(name = "quantity")
    public Double quantity;

    @Column(name = "price")
    public Double price;

    @NotNull
    @Column(name = "created_at")
    public Timestamp created_at;

    @Nullable
    @Column(name = "update_at")
    public Timestamp updated_at;
}
