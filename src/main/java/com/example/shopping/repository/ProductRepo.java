package com.example.shopping.repository;

import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findProductsBySupplier(Supplier supplier);
    Long deleteBySupplier(int id);
    @Modifying
    @Query("update Product set price = ?1, sup_id = ?2 where id = ?3")
    @Transactional
    int updateProductInfoById(double price, int supid, int id);
}
