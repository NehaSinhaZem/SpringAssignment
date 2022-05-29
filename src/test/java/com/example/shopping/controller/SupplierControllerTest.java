package com.example.shopping.controller;

import com.example.shopping.convertor.SupplierMapper;
import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
class SupplierControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    @Mock
    private SupplierService supplierService;
    @InjectMocks
    private SupplierController supplierController;
    SupplierMapper supplierMapper = new SupplierMapper();
    Supplier supplier1 = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Supplier supplier2 = new Supplier(102,"S2","1231031890","s2@gmail.com",new ArrayList<>());
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(supplierController).build();
    }
    @Test
    void getSuppliers() throws Exception {
        List<SupplierDto> supplierDtos = supplierMapper.getDtoList(new ArrayList<>(Arrays.asList(supplier1,supplier2)));
        Mockito.when(supplierService.getSuppliers()).thenReturn(supplierDtos);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/suppliers/list")
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void showAddForm() throws Exception {
        this.mockMvc.perform(get("/suppliers/add")).andExpect(status().isOk());
    }

    @Test
    void saveSupplier() throws Exception {
        Mockito.when(supplierService.saveSupplier(supplierMapper.convertToDto(supplier1))).thenReturn(supplier1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/suppliers/supplier")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .content(this.mapper.writeValueAsString(supplier1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/suppliers/list"));
    }

    @Test
    void updateSupplier() throws Exception {
        supplier1.setPhone("3456789800");
        Mockito.when(supplierService.updateSupplier(1,supplierMapper.convertToDto(supplier1))).thenReturn(1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/suppliers/supplier/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .content(this.mapper.writeValueAsString(supplier1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/suppliers/list")).andReturn();
    }

    @Test
    void updateSupplierForm() throws Exception {
        this.mockMvc.perform(get("/suppliers/supplier/update").param("id","1")).andExpect(status().isOk());
    }

    @Test
    void deleteSupplier() throws Exception {
        supplierService.deleteSupplier(1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/suppliers/delete")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL)
                .secure( true )
                .param("id","1")
                .content(this.mapper.writeValueAsString(supplier1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/suppliers/list"));
    }
}