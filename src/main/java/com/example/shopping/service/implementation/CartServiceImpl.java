package com.example.shopping.service.implementation;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.Product;
import com.example.shopping.repository.CustomerRepo;
import com.example.shopping.service.CartService;
import com.example.shopping.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CustomerService customerService;
    private ProductMapper productMapper = new ProductMapper();
    @Override
    public List<ProductDto> getProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            Customer customer = customerService.findCustomerByEmail(auth.getName());
            return productMapper.getDtoList(customer.getProductList());
        }
        return new ArrayList<>();
    }

    @Override
    public void removeProduct(int pid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
            Customer customer= customerService.findCustomerByEmail(auth.getName());
            List<Product> productList= customer.getProductList();
            for(int i=0;i<productList.size();i++)
                if(productList.get(i).getId()==pid){
                    productList.remove(i);
                    break;
                }
            customerRepo.save(customer);
        }
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
           Customer customer = customerService.findCustomerByEmail(auth.getName());
           List<Product> productList= customer.getProductList();
           if(productList==null)
                productList = new ArrayList<>();
           productList.add(productMapper.convertToEntity(productDto));
           customerRepo.save(customer);
           return productMapper.convertToEntity(productDto);
        }
        return null;
    }
}
