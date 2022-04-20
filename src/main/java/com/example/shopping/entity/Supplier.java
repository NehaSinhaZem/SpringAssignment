package com.example.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="name") @NotNull(message = "Name cannot be empty")
    private String name;
    @Column(name = "phone") @Size(message = "Length should be 10")
    private String phone;
    @Column(name = "email") @Email(message = "Invalid email")
    private String email;
}
