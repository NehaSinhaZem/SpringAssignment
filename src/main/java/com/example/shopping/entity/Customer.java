package com.example.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Table(name="customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
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
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "cart",joinColumns = @JoinColumn(name = "cust_id"),inverseJoinColumns = @JoinColumn(name = "prod_id"))
    List<Product> productList;
}
