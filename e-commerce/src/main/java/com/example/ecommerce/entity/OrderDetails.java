package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderDetails {
    public Long productId;
    public Double productPrice;
    public Double productQuantity;
}
