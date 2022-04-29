package com.example.shopping.service;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.repository.ProductRepo;
import com.example.shopping.service.implementation.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @Mock
    private SupplierService supplierService;
    @InjectMocks
    private ProductServiceImpl productService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    private MockMvc mockMvc;
    Supplier supplier = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Product product = new Product(1,"P1",1000,supplier);
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productService).build();
    }
    @Test
    @DisplayName("Should Retrieve All Products")
    public void shouldFindProducts() {
        List<Product> expectedPostResponse = new ArrayList<>(Arrays.asList(
                new Product(1, "P1", 1000,
                        new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>()))));

        Mockito.when(productRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(product)));
//        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        List<Product> actualPostResponse = productService.getProducts();

        Assertions.assertThat(actualPostResponse.get(0).getId()).isEqualTo(expectedPostResponse.get(0).getId());
        Assertions.assertThat(actualPostResponse.get(0).getName()).isEqualTo(expectedPostResponse.get(0).getName());
        Assertions.assertThat(actualPostResponse.get(0).getPrice()).isEqualTo(expectedPostResponse.get(0).getPrice());
    }
    @Test
    @DisplayName("Should Save Product")
    public void shouldSaveProduct() {
        productService.saveProduct(product);
        Mockito.verify(productRepo, Mockito.times(1)).save(productArgumentCaptor.capture());

        Assertions.assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("P1");
        Assertions.assertThat(productArgumentCaptor.getValue().getId()).isEqualTo(1);
        Assertions.assertThat(productArgumentCaptor.getValue().getPrice()).isEqualTo(1000);
    }
}
