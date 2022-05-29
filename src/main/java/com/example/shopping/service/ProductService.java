package com.example.shopping.service;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();
    Product saveProduct(ProductDto productDto);
    void deleteProduct(int id);
    ProductDto findProduct(int id);

    int updateProduct(int id, ProductDto postRequest);
}
