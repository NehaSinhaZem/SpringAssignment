package com.example.shopping.dao;

import com.example.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findAll();
    List<Product> findProductsBysupid(int id);
    Long deleteBysupid(int id);
}
