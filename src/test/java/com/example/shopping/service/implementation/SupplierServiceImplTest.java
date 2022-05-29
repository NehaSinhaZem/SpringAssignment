package com.example.shopping.service.implementation;

import com.example.shopping.convertor.SupplierMapper;
import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Authority;
import com.example.shopping.entity.Supplier;
import com.example.shopping.entity.User;
import com.example.shopping.repository.AuthorityRepo;
import com.example.shopping.repository.SupplierRepo;
import com.example.shopping.repository.UserRepo;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class SupplierServiceImplTest {
    @Mock
    private SupplierRepo supplierRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private AuthorityRepo authorityRepo;
    @InjectMocks
    private SupplierServiceImpl supplierService;
    private final SupplierMapper supplierMapper = new SupplierMapper();
    Supplier supplier1 = new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
    Supplier supplier2 = new Supplier(102,"S2","1231031890","s2@gmail.com",new ArrayList<>());
    @BeforeEach
    public void setUp(){
        MockMvcBuilders.standaloneSetup(supplierService).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getSuppliers() {
        List<Supplier> expectedGetResponse = new ArrayList<>(List.of(
                new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>())));

        Mockito.when(supplierRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(supplier1,supplier2)));

        List<SupplierDto> actualPostResponse = supplierService.getSuppliers();

        Assertions.assertThat(actualPostResponse.get(0).getId()).isEqualTo(expectedGetResponse.get(0).getId());
        Assertions.assertThat(actualPostResponse.get(0).getName()).isEqualTo(expectedGetResponse.get(0).getName());
        Assertions.assertThat(actualPostResponse.get(0).getEmail()).isEqualTo(expectedGetResponse.get(0).getEmail());
    }

    @Test
    void saveSupplier() {
        Supplier expectedPostResponse =  new Supplier(101,"S1","1234567890","s1@gmail.com",new ArrayList<>());
        User user = new User(supplier1.getEmail(),"test123",1);
        Authority authority = new Authority(supplier1.getEmail(),"ROLE_SUPPLIER");
        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
        Mockito.when(authorityRepo.save(any(Authority.class))).thenReturn(authority);
        Mockito.when(supplierRepo.save(any(Supplier.class))).thenReturn(supplier1);
        Supplier actualPostResponse = supplierService.saveSupplier(supplierMapper.convertToDto(supplier1));
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getEmail()).isEqualTo(expectedPostResponse.getEmail());
    }

    @Test
    void deleteSupplier() {
        User user = new User(supplier1.getEmail(),"test123",1);
        Mockito.when(userRepo.findByUsername(supplier1.getEmail())).thenReturn(user);
        user.setEnabled(0);
        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
        supplierService.deleteSupplier(101);
        assertThrows(
                RuntimeException.class,
                () -> supplierService.findSupplier(1));
    }

    @Test
    void findSupplier() {
        Supplier expectedGetResponse = new Supplier(102,"S2","1231031890","s2@gmail.com",new ArrayList<>());

        Mockito.when(supplierRepo.findById(1)).thenReturn(Optional.of(expectedGetResponse));

        SupplierDto actualGetResponse = supplierService.findSupplier(1);

        Assertions.assertThat(actualGetResponse.getId()).isEqualTo(expectedGetResponse.getId());
        Assertions.assertThat(actualGetResponse.getName()).isEqualTo(expectedGetResponse.getName());
    }

    @Test
    void testFindSupplier() {
        Supplier expectedGetResponse = new Supplier(102,"S2","1231031890","s2@gmail.com",new ArrayList<>());

        Mockito.when(supplierRepo.findByEmail("s2@gmail.com")).thenReturn(expectedGetResponse);

        SupplierDto actualGetResponse = supplierService.findSupplier("s2@gmail.com");

        Assertions.assertThat(actualGetResponse.getId()).isEqualTo(expectedGetResponse.getId());
        Assertions.assertThat(actualGetResponse.getName()).isEqualTo(expectedGetResponse.getName());
    }

    @Test
    void updateSupplier() {
        supplier1.setPhone("328992200");
        int expectedGetResponse = 1;
        Mockito.when(supplierService.updateSupplier(1,supplierMapper.convertToDto(supplier1))).thenReturn(1);
        int actualGetResponse = supplierService.updateSupplier(1,supplierMapper.convertToDto(supplier1));
        assert expectedGetResponse==actualGetResponse;
    }
}