package com.example.shopping.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="product")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="name") @NotNull(message="Name cannot be empty") @Size(min = 5,message = "Length should atleast be 5")
    private String name;
    @Column(name = "price")
    private double price;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "sup_id")
    private Supplier supplier;
}
