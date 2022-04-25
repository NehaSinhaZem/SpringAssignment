package com.example.shopping.repository;

import com.example.shopping.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface SupplierRepo extends JpaRepository<Supplier,Integer> {

    Supplier findByEmail(String name);
    @Modifying
    @Query("update Supplier set phone = ?1 where id = ?2")
    @Transactional
    int updateSupplierInfoById(String phone, int id);
}
