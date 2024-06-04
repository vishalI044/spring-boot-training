package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String userName;
    public String password;
    public String name;
    public String phone_number;
    public String email;
    public String hash;
    public Timestamp created_at;
    public String created_by;
    public Timestamp updated_at;
    public String updated_by;
}
