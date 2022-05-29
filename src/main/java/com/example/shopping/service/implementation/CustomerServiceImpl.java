package com.example.shopping.service.implementation;

import com.example.shopping.entity.Customer;
import com.example.shopping.repository.CustomerRepo;
import com.example.shopping.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public Customer saveCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

}
