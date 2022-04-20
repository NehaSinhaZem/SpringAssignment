package com.example.shopping.service;

import com.example.shopping.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    void saveProduct(Product product);
    void deleteProduct(int id);
    Product findProduct(int id);

    List<Product> getProductsBySupID(int id);
    Long deleteBySupID(int id);
}
