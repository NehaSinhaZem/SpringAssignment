package com.example.shopping.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="supplier")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") @Getter @Setter
    private int id;
    @Column(name="name") @NotNull(message = "Name cannot be empty") @Getter @Setter
    private String name;
    @Column(name = "phone") @Size(message = "Length should be 10") @Getter @Setter
    private String phone;
    @Column(name = "email") @Email(message = "Invalid email") @Getter @Setter
    private String email;
    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL) @Getter @Setter
    List<Product> productList;
}
