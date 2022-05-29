package com.example.shopping.service.implementation;

import com.example.shopping.entity.Customer;
import com.example.shopping.repository.CustomerRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CustomerServiceImplTest {
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepo customerRepo;
    Customer customer = new Customer(1,"Test Customer 1","5396384100","c1@gmail.com", new ArrayList<>());

    @BeforeEach
    public void setUp(){
        MockMvcBuilders.standaloneSetup(customerService).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void saveCustomer() {
        Customer expectedPostResponse = new Customer(1,"Test Customer 1","5396384100","c1@gmail.com", new ArrayList<>());
        Mockito.when(customerRepo.save(any (Customer.class))).thenReturn(expectedPostResponse);
        Customer actualPostResponse = customerService.saveCustomer(customer);
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
    }

    @Test
    void findCustomerByEmail() {
        Customer expectedGetResponse = new Customer(1,"Test Customer 1","5396384100","c1@gmail.com", new ArrayList<>());
        Mockito.when(customerRepo.findByEmail("c1@gmail.com")).thenReturn(expectedGetResponse);

        Customer actualGetResponse = customerService.findCustomerByEmail("c1@gmail.com");

        Assertions.assertThat(actualGetResponse.getId()).isEqualTo(expectedGetResponse.getId());
    }
}