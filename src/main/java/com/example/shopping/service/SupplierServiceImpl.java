package com.example.shopping.service;

import com.example.shopping.dao.SupplierRepo;
import com.example.shopping.entity.Supplier;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service @AllArgsConstructor
public class SupplierServiceImpl implements SupplierService{
    @Autowired
    private SupplierRepo supplierRepo;
    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepo.findAll();
    }

    @Override
    public void saveSupplier(Supplier supplier) {
        supplierRepo.save(supplier);
    }

    @Override
    public void deleteSupplier(int id) {
        supplierRepo.deleteById(id);
    }

    @Override
    public Supplier findSupplier(int id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if(supplier.isPresent())
            return supplier.get();
        throw new RuntimeException("Supplier not found");
    }

    @Override
    public int findSupplierId(String name) {
        List<Supplier> suppliers = supplierRepo.findByEmail(name);
        return suppliers.get(0).getId();
    }
}
