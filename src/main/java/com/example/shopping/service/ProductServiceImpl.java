package com.example.shopping.service;

import com.example.shopping.dao.ProductRepo;
import com.example.shopping.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor @Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public Product findProduct(int id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent())
            return product.get();
        throw new RuntimeException("Product not found");
    }

    public List<Product> getProductsBySupID(int id) {
        System.out.println(id);
        return productRepo.findProductsBysupid(id);
    }

    @Override @Transactional
    public Long deleteBySupID(int supid) {
        return productRepo.deleteBysupid(supid);
    }
}
