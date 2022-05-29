package com.example.shopping.service.implementation;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.repository.CustomerRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CartServiceImplTest {
    @Mock
    private CustomerServiceImpl customerService;
    @InjectMocks
    private CartServiceImpl cartService;
    @Mock private CustomerRepo customerRepo;
    Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Product product1 = new Product(1,"Test Product 1",100,supplier,new ArrayList<>());
    Product product2 = new Product(2,"Test Product 2",10,supplier,new ArrayList<>());
    Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",Arrays.asList(product1,product2));
    @BeforeEach
    public void setUp(){
        MockMvcBuilders.standaloneSetup(cartService).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test @WithMockUser(username = "c1@gmail.com",roles = "CUSTOMER")
    void getProducts() {
        Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>(Arrays.asList(product1,product2)));
        List<Product> expectedGetResponse = new ArrayList<>(Arrays.asList(product1,product2));
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerService.findCustomerByEmail("c1@gmail.com")).thenReturn(customer);

        List<ProductDto> actualPostResponse = cartService.getProducts();
        Assertions.assertThat(actualPostResponse).isNotNull();
        org.junit.jupiter.api.Assertions.assertEquals(2,actualPostResponse.size());
        Assertions.assertThat(actualPostResponse).isNotEmpty();
    }

    @Test @WithMockUser(username = "c1@gmail.com",roles = "CUSTOMER")
    void removeProduct() {
        Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>(Arrays.asList(product1,product2)));
        Mockito.when(customerService.findCustomerByEmail("c1@gmail.com")).thenReturn(customer);
        List<Product> productList = customer.getProductList();
        Assertions.assertThat(productList).contains(product1);
        List<ProductDto> actualPostResponse = cartService.getProducts();
        Assertions.assertThat(actualPostResponse).isNotNull();
        cartService.removeProduct(1);
        List<ProductDto> newPostResponse = cartService.getProducts();
        Assertions.assertThat(newPostResponse).isNotNull();
        Assertions.assertThat(newPostResponse).doesNotContain(new ProductMapper().convertToDto(product1));
    }

    @Test @WithMockUser(username = "c1@gmail.com",roles = "CUSTOMER")
    void addProduct() {
        Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>(Arrays.asList(product1,product2)));
        Product expectedPostResponse = new Product(1,"Test Product 1",100,supplier,List.of(customer));
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerService.findCustomerByEmail(customer.getEmail())).thenReturn(customer);
        Product actualPostResponse = cartService.addProduct(new ProductMapper().convertToDto(product1));
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
    }
}