package com.example.shopping.service.implementation;

import com.example.shopping.convertor.SupplierMapper;
import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Authority;
import com.example.shopping.entity.User;
import com.example.shopping.repository.AuthorityRepo;
import com.example.shopping.repository.SupplierRepo;
import com.example.shopping.entity.Supplier;
import com.example.shopping.repository.UserRepo;
import com.example.shopping.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthorityRepo authorityRepo;
    private final SupplierMapper supplierMapper= new SupplierMapper();
    @Override
    public List<SupplierDto> getSuppliers() {
        return supplierMapper.getDtoList(supplierRepo.findAll());
    }
    @Override
    public Supplier saveSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierRepo.save(supplierMapper.convertToEntity(supplierDto));
        User user = new User(supplierDto.getEmail(),"{noop}test123",1);
        userRepo.save(user);
        Authority authority = new Authority(supplier.getEmail(),"ROLE_SUPPLIER");
        authorityRepo.save(authority);
        return supplier;
    }

    @Override
    public void deleteSupplier(int id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if(supplier.isEmpty())
            return;
        User user=userRepo.findByUsername(supplier.get().getEmail());
        user.setEnabled(0);
        userRepo.save(user);
        supplierRepo.deleteById(id);
    }

    @Override
    public SupplierDto findSupplier(int id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if(supplier.isPresent())
            return supplierMapper.convertToDto(supplier.get());
        throw new RuntimeException("Supplier not found");
    }

    @Override
    public SupplierDto findSupplier(String name) {
        return supplierMapper.convertToDto(supplierRepo.findByEmail(name));
    }

    @Override @Transactional
    public int updateSupplier(int id, SupplierDto supplierDto) {
        return supplierRepo.updateSupplierInfoById(supplierDto.getPhone(),id);
    }
}
