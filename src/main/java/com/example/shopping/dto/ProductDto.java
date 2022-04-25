package com.example.shopping.dto;

import com.example.shopping.entity.Supplier;
import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private double price;
    private Supplier supplier;
}
