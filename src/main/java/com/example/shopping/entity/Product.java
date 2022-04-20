package com.example.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="name") @NotNull(message="Name cannot be empty")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "sup_id") @NotNull(message = "Supplier cannot be empty")
    private int supid;
}
