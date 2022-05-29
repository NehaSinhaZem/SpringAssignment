package com.example.shopping.service;

import com.example.shopping.entity.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer findCustomerByEmail(String email);
//    Customer findCustomer(int id);
}
