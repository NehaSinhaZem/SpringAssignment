package com.example.shopping.controller;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@AllArgsConstructor @NoArgsConstructor
public class ProductController {
//    @Autowired
    List<Product> productList;
    @Autowired
    private ProductService service;
    public ProductController(ProductService service){
        this.service=service;
    }
    @Autowired
    private SupplierService supplierService;
    public ProductController(ProductService productService,SupplierService supplierService){
        service=productService;
        this.supplierService=supplierService;
    }
    @Autowired
    public ModelMapper modelMapper;

    @GetMapping("/add")
    public String showAddForm(Model model){
        Product product = new Product();
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("product",product);
        model.addAttribute("supplier",suppliers);
        return "add-product";
    }
    @GetMapping("/remove")
    public String deleteProduct(@RequestParam("id") int id){
        service.deleteProduct(id);
        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String getProducts(Model model){
        List<ProductDto> dtoList= service.getProducts().stream().map(post -> modelMapper.map(post, ProductDto.class))
                .collect(Collectors.toList());
        model.addAttribute("products",dtoList);
        return "list-products";
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id){
        Product product = service.findProduct(id);
        if(product==null)
            throw new RuntimeException("Employee not found");
        ProductDto getResponse = modelMapper.map(product, ProductDto.class);

        return ResponseEntity.ok().body(getResponse);
    }
    @PostMapping(path = "/product",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveProduct(ProductDto dto){
            // convert DTO to entity
            Product postRequest = modelMapper.map(dto, Product.class);

            Product post = service.saveProduct(postRequest);

            ProductDto postResponse = modelMapper.map(post, ProductDto.class);

            return "redirect:/products/list";
    }
    @PostMapping(path = "/product/{id}")
    public String updateProduct(@PathVariable int id, ProductDto dto){
        Product product = modelMapper.map(dto, Product.class);
        int res = service.updateProduct(id,product);

        return "redirect:/products/list";
    }
    @RequestMapping(path = "/product/update")
    public String updateProductForm(@RequestParam("id") int id,Model model){
        ProductDto productDto = modelMapper.map(service.findProduct(id),ProductDto.class);
        model.addAttribute("product",productDto);
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("supplier",suppliers);
        return "update-product";
    }
}
