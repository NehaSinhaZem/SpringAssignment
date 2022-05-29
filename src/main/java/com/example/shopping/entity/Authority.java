package com.example.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Authority {
    @Column(name = "username") @Id
    String username;
    @Column(name = "authority")
    String role;
}
