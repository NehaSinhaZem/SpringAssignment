package com.example.shopping.controller;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.CartService;
import com.example.shopping.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CartControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    @InjectMocks
    private CartController cartController;
    @Mock
    private CartService cartService;
    @Mock
    private ProductService productService;
    private ProductMapper productMapper = new ProductMapper();
    Customer customer = new Customer(201,"C1","2798793902","c1@gmail.com",new ArrayList<>());
    Product product1 = new Product(1,"Test Product 1",100,new Supplier(), List.of(customer));
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void addToCart() throws Exception {
        Mockito.when(cartService.addProduct(productMapper.convertToDto(product1))).thenReturn(product1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/cart/add")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .param("id","1")
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/cart/list"));
    }

    @Test
    void removeFromCart() throws Exception {
        cartService.removeProduct(1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/cart/remove")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .param("id","1")
                .content(this.mapper.writeValueAsString(product1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/cart/list"));
    }

    @Test
    void viewCart() throws Exception {
        List<ProductDto> productList = productMapper.getDtoList(new ArrayList<>(Arrays.asList(product1)));
        Mockito.when(cartService.getProducts()).thenReturn(productList);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/cart/list")
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }
}