package com.example.shopping.service;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;

import java.util.List;

public interface CartService {
    List<ProductDto> getProducts();
    void removeProduct(int pid);
    Product addProduct(ProductDto productDto);
}
