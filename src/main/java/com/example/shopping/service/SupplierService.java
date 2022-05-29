package com.example.shopping.service;

import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Supplier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SupplierService {

    List<SupplierDto> getSuppliers();
    Supplier saveSupplier(SupplierDto supplierDto);

    void deleteSupplier(int id);
    SupplierDto findSupplier(int id);
    SupplierDto findSupplier(String name);

    @Transactional
    int updateSupplier(int id, SupplierDto supplierDto);
}
