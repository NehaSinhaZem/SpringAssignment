package com.example.shopping.dao;

import com.example.shopping.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepo extends JpaRepository<Supplier,Integer> {
    List<Supplier> findAll();

    List<Supplier> findByEmail(String name);
}
