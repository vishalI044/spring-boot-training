package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public Long user_id;
    public OrderDetails orderDetails;
    public String status;
    public Timestamp created_at;
    public String created_by;
    public Timestamp updated_at;
    public String updated_by;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserDetails userDetails;
}
