package com.example.shopping.dto;

import com.example.shopping.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @RequiredArgsConstructor
public class SupplierDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    List<Product> productList;
}
