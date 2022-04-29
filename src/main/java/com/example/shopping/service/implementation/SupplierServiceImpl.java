package com.example.shopping.service.implementation;

import com.example.shopping.aspect.RepoLoggingAspect;
import com.example.shopping.entity.Authority;
import com.example.shopping.entity.User;
import com.example.shopping.repository.AuthorityRepo;
import com.example.shopping.repository.SupplierRepo;
import com.example.shopping.entity.Supplier;
import com.example.shopping.repository.UserRepo;
import com.example.shopping.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service @AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthorityRepo authorityRepo;
    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepo.findAll();
    }
    @Override
    public Supplier saveSupplier(Supplier supplier) {
        supplierRepo.save(supplier);
        User user = new User(supplier.getEmail(),"{noop}test123",1);
        userRepo.save(user);
        Authority authority = new Authority(user.getUsername(),"ROLE_SUPPLIER");
        authorityRepo.save(authority);
        return supplier;
    }

    @Override
    public void deleteSupplier(int id) {
        User user=userRepo.findByUsername(supplierRepo.findById(id).get().getEmail());
        Logger logger = Logger.getLogger(RepoLoggingAspect.class.getName());
        logger.info(supplierRepo.findById(id).get().getEmail());
        user.setEnabled(0);
        userRepo.save(user);
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
    public Supplier findSupplier(String name) {
        Supplier supplier = supplierRepo.findByEmail(name);
        return supplier;
    }

    @Override @Transactional
    public int updateSupplier(int id, Supplier postRequest) {
        return supplierRepo.updateSupplierInfoById(postRequest.getPhone(),id);
    }
}
