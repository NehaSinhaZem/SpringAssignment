package com.example.shopping.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class User {
    @Id
    private String username;
    private String password;
    private int enabled;
}
