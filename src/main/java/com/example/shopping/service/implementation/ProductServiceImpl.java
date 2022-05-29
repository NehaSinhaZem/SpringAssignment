package com.example.shopping.service.implementation;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.convertor.SupplierMapper;
import com.example.shopping.dto.ProductDto;
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
    private ProductMapper productMapper= new ProductMapper();
    private SupplierMapper supplierMapper= new SupplierMapper();
    @Override
    public List<ProductDto> getProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier=supplierMapper.convertToEntity(supplierService.findSupplier(auth.getName()));
            return productMapper.getDtoList(supplier.getProductList());
        }
        List<Product> productList = productRepo.findAll();
        return productMapper.getDtoList(productList);
    }
    @Override
    public Product saveProduct(ProductDto productDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier= supplierMapper.convertToEntity(supplierService.findSupplier(auth.getName()));
            productDto.setSupplier(supplier);
        }
        Product product = productMapper.convertToEntity(productDto);
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public ProductDto findProduct(int id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent())
            return productMapper.convertToDto(product.get());
        throw new ProductNotFoundException("Product not found");
    }

    @Override @Transactional
    public int updateProduct(int id, ProductDto productDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            Supplier supplier = supplierMapper.convertToEntity(supplierService.findSupplier(auth.getName()));
            productDto.setSupplier(supplier);
        }
        return productRepo.updateProductInfoById(productDto.getPrice(),productDto.getSupplier().getId(),id);
    }
}
