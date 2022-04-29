package com.example.shopping.service;

import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product saveProduct(Product product);
    void deleteProduct(int id);
    Product findProduct(int id);

    int updateProduct(int id, Product postRequest);
}
