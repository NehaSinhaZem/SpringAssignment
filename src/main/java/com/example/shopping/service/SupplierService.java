package com.example.shopping.service;

import com.example.shopping.entity.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> getSuppliers();
    Supplier saveSupplier(Supplier supplier);
    void deleteSupplier(int id);
    Supplier findSupplier(int id);
    Supplier findSupplier(String name);
//    int findSupplierId(String name);

    int updateSupplier(int id, Supplier supplier);
}
