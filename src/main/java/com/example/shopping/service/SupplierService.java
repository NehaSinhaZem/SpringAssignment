package com.example.shopping.service;

import com.example.shopping.entity.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> getSuppliers();
    void saveSupplier(Supplier supplier);
    void deleteSupplier(int id);
    Supplier findSupplier(int id);

    int findSupplierId(String name);
}
