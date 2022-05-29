package com.example.shopping.repository;

import com.example.shopping.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String name);
}
