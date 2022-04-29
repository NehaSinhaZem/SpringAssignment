package com.example.shopping.service.implementation;

import com.example.shopping.entity.Supplier;
import com.example.shopping.exception.ProductNotFoundException;
import com.example.shopping.repository.ProductRepo;
import com.example.shopping.entity.Product;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SupplierService supplierService;
    @Override
    public List<Product> getProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier=supplierService.findSupplier(auth.getName());
            return supplier.getProductList();
        }
        return productRepo.findAll();
    }
    @Override
    public Product saveProduct(Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier= supplierService.findSupplier(auth.getName());
            product.setSupplier(supplier);
        }
        productRepo.save(product);
        return product;
    }

    @Override
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public Product findProduct(int id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent())
            return product.get();
        throw new ProductNotFoundException("Product not found");
    }

    public List<Product> getProductsBySupplier(Supplier supplier) {
        return productRepo.findProductsBySupplier(supplier);
    }

    @Override @Transactional
    public int updateProduct(int id, Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier = supplierService.findSupplier(auth.getName());
            product.setSupplier(supplier);
        }
        return productRepo.updateProductInfoById(product.getPrice(),product.getSupplier().getId(),id);
    }
}
