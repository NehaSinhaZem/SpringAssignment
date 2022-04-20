package com.example.shopping;

import com.example.shopping.controller.ProductController;
import com.example.shopping.entity.Product;
import com.example.shopping.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;
    Product product1 = new Product(1,"Test Product 1",100,101);
    Product product2 = new Product(2,"Test Product 2",10,102);
    Product product3 = new Product(3,"Test Product 3",0,101);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }
    @Test
    public void getAllProducts_success() throws Exception{
        List<Product> productList = new ArrayList<>(Arrays.asList(product1,product2,product3));
        Mockito.when(productService.getProducts()).thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/list").contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }
}
