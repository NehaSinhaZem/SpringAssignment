package com.example.shopping.service.implementation;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.convertor.SupplierMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.exception.ProductNotFoundException;
import com.example.shopping.repository.ProductRepo;
import com.example.shopping.service.SupplierService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ProductServiceImplTest {
    @Mock
    private ProductRepo productRepo;
    @Mock
    private SupplierService supplierService;
    @InjectMocks
    private ProductServiceImpl productService;
    private ProductMapper productMapper = new ProductMapper();
    Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>());
    Product product1 = new Product(1,"Test Product 1",100,supplier,List.of(customer));
    Product product2 = new Product(2,"Test Product 2",10,supplier,List.of(customer));
    Product product3 = new Product(3,"Test Product 3",0,supplier,List.of(customer));
    @BeforeEach
    public void setUp(){
        MockMvcBuilders.standaloneSetup(productService).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getProducts() {
        List<Product> expectedGetResponse = new ArrayList<>(Arrays.asList(product1,product2,product3));

        Mockito.when(productRepo.findAll()).thenReturn(expectedGetResponse);
        List<ProductDto> actualPostResponse = productService.getProducts();
        Assertions.assertThat(actualPostResponse).isNotNull();
        org.junit.jupiter.api.Assertions.assertEquals(3,actualPostResponse.size());
        Assertions.assertThat(actualPostResponse).isNotEmpty();
        Assertions.assertThat(actualPostResponse.get(0).getId()).isEqualTo(expectedGetResponse.get(0).getId());
        Assertions.assertThat(actualPostResponse.get(0).getName()).isEqualTo(expectedGetResponse.get(0).getName());
        Assertions.assertThat(actualPostResponse.get(0).getPrice()).isEqualTo(expectedGetResponse.get(0).getPrice());
    }
    @Test @WithMockUser(username = "s1@gmail.com",roles = "SUPPLIER")
    void testGetProducts() {
        Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>(Arrays.asList(product1,product2,product3)));
        List<Product> expectedGetResponse = new ArrayList<>(Arrays.asList(product1,product2,product3));

        Mockito.when(supplierService.findSupplier("s1@gmail.com")).thenReturn(new SupplierMapper().convertToDto(supplier));
        List<Product> actualPostResponse = supplier.getProductList();
        Assertions.assertThat(actualPostResponse).isNotNull();
        org.junit.jupiter.api.Assertions.assertEquals(3,actualPostResponse.size());
        Assertions.assertThat(actualPostResponse).isNotEmpty();
        Assertions.assertThat(actualPostResponse.get(0).getId()).isEqualTo(expectedGetResponse.get(0).getId());
        Assertions.assertThat(actualPostResponse.get(0).getName()).isEqualTo(expectedGetResponse.get(0).getName());
        Assertions.assertThat(actualPostResponse.get(0).getPrice()).isEqualTo(expectedGetResponse.get(0).getPrice());
    }
    @Test
    void saveProduct() {
        Product expectedPostResponse = new Product(1,"Test Product 1",100,supplier,List.of(customer));
        Mockito.when(productRepo.save(any (Product.class))).thenReturn(expectedPostResponse);
        Product actualPostResponse = productService.saveProduct(productMapper.convertToDto(product1));
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getPrice()).isEqualTo(expectedPostResponse.getPrice());
    }
    @Test @WithMockUser(username = "s1@gmail.com",roles = "SUPPLIER")
    void testSaveProduct() {
        Product expectedPostResponse = new Product(1,"Test Product 1",100,supplier,List.of(customer));
        Mockito.when(supplierService.findSupplier("s1@gmail.com")).thenReturn(new SupplierMapper().convertToDto(supplier));
        product1.setSupplier(supplier);
        Mockito.when(productRepo.save(any (Product.class))).thenReturn(expectedPostResponse);
        Product actualPostResponse = productService.saveProduct(productMapper.convertToDto(product1));
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getPrice()).isEqualTo(expectedPostResponse.getPrice());
    }
    @Test
    void deleteProduct() {
        productService.deleteProduct(1);
        assertThrows(
                ProductNotFoundException.class,
                () -> productService.findProduct(1));
    }

    @Test
    void findProduct() {
        Product expectedGetResponse = new Product(1, "P1", 1000,
                new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>()),List.of(customer));

        Mockito.when(productRepo.findById(1)).thenReturn(Optional.ofNullable(expectedGetResponse));

        ProductDto actualGetResponse = productService.findProduct(1);

        Assertions.assertThat(actualGetResponse.getId()).isEqualTo(expectedGetResponse.getId());
        Assertions.assertThat(actualGetResponse.getName()).isEqualTo(expectedGetResponse.getName());
    }
    @Test
    void updateProduct() {
        product1.setPrice(200);
        int expectedGetResponse = 1;
        Mockito.when(productService.updateProduct(1,productMapper.convertToDto(product1))).thenReturn(1);

        int actualGetResponse = productService.updateProduct(1,productMapper.convertToDto(product1));
        System.out.println(actualGetResponse);
        assert expectedGetResponse==actualGetResponse;
    }
    @Test @WithMockUser(username = "s1@gmail.com",roles = "SUPPLIER")
    void testUpdateProduct() {
        Mockito.when(supplierService.findSupplier("s1@gmail.com")).thenReturn(new SupplierMapper().convertToDto(supplier));
        product1.setSupplier(supplier);
        product1.setPrice(200);
        int expectedGetResponse = 1;
        Mockito.when(productService.updateProduct(1,productMapper.convertToDto(product1))).thenReturn(1);

        int actualGetResponse = productService.updateProduct(1,productMapper.convertToDto(product1));
        System.out.println(actualGetResponse);
        assert expectedGetResponse==actualGetResponse;
    }
}