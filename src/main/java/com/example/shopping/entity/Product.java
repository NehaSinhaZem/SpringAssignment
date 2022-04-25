package com.example.shopping.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="product")
@AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") @Getter @Setter
    private int id;
    @Column(name="name") @NotNull(message="Name cannot be empty") @Size(min = 5,message = "Name cannot be empty") @Getter @Setter
    private String name;
    @Column(name = "price") @Getter @Setter
    private double price;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "sup_id") @Getter @Setter
    private Supplier supplier;
}
