package com.example.shopping.controller;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    @Mock
    private ProductService productService;
    @Mock
    private SupplierService supplierService;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductController productController;
    Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>());
    Product product1 = new Product(1,"Test Product 1",100,supplier,List.of(customer));
    Product product2 = new Product(2,"Test Product 2",10,supplier,List.of(customer));
    Product product3 = new Product(3,"Test Product 3",0,supplier,List.of(customer));
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }
    @Test
    void showAddForm() throws Exception {
        this.mockMvc.perform(get("/products/add")).andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        productService.deleteProduct(1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/products/remove")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .param("id","1")
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/products/list"));
    }

    @Test
    void getProducts() throws Exception {
        List<ProductDto> productList = productMapper.getDtoList(new ArrayList<>(Arrays.asList(product1,product2,product3)));
        Mockito.when(productService.getProducts()).thenReturn(productList);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/list")
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }
    @Test
    void getProduct() throws Exception {
        Mockito.when(productService.findProduct(1)).thenReturn(new ProductMapper().convertToDto(product1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void saveProduct() throws Exception {
        Mockito.when(productService.saveProduct(productMapper.convertToDto(product1))).thenReturn(product1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/product")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/products/list"));
    }

    @Test
    void updateProduct() throws Exception {
        product1.setPrice(product2.getPrice());
        Mockito.when(productService.updateProduct(1,productMapper.convertToDto(product1))).thenReturn(1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/product/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/products/list")).andReturn();
    }

    @Test
    void updateProductForm() throws Exception {
        this.mockMvc.perform(get("/products/product/update").param("id","1")).andExpect(status().isOk());
    }
}