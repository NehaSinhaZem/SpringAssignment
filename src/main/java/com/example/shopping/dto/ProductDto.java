package com.example.shopping.dto;

import com.example.shopping.entity.Supplier;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private double price;
    private Supplier supplier;
}
