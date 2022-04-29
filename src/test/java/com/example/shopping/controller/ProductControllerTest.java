package com.example.shopping.controller;

import com.example.shopping.controller.ProductController;
import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    @Mock
    private ProductService productService;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductController productController;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Product product1 = new Product(1,"Test Product 1",100,supplier);
    Product product2 = new Product(2,"Test Product 2",10,supplier);
    Product product3 = new Product(3,"Test Product 3",0,supplier);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }
    @Test
    public void getAllProducts_success() throws Exception{
        List<Product> productList = new ArrayList<>(Arrays.asList(product1,product2,product3));
        Mockito.when(productService.getProducts()).thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/list")
                        .contentType(MediaType.ALL))
                        .andExpect(status().isOk());
    }

    @Test @WithMockUser(username="admin",roles={"SUPPLIER","ADMIN"})
    public void saveProduct_success() throws Exception{
//        productService.saveProduct(product1);
        given(productService.saveProduct(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));
//        Mockito.when(productService.saveProduct(product1)).thenReturn(product1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/product")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }
}
