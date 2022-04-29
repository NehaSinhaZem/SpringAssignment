package com.example.shopping.controller;

import com.example.shopping.convertor.ProductMapper;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    List<Product> productList;
    @Autowired
    private ProductService service;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    public ProductMapper mapper;

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
        List<ProductDto> dtoList= mapper.getDtoList(service.getProducts());
        model.addAttribute("products",dtoList);
        return "list-products";
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id){
        Product product = service.findProduct(id);
        if(product==null)
            throw new RuntimeException("Product not found");
        ProductDto productDto = mapper.convertToDto(product);

        return ResponseEntity.ok().body(productDto);
    }
    @PostMapping(path = "/product")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult){
            // convert DTO to entity ,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
        if(!bindingResult.hasErrors()) {
            Product newProduct = mapper.convertToEntity(productDto);
            Product product = service.saveProduct(newProduct);
            ProductDto postResponse = mapper.convertToDto(product);
            return "redirect:/products/list";
        }
//        else
            return null;
    }
    @PostMapping(path = "/product/{id}")
    public String updateProduct(@PathVariable int id, ProductDto productDto){
        Product product = mapper.convertToEntity(productDto);
        int res = service.updateProduct(id,product);

        return "redirect:/products/list";
    }
    @RequestMapping(path = "/product/update")
    public String updateProductForm(@RequestParam("id") int id,Model model){
        ProductDto productDto = mapper.convertToDto(service.findProduct(id));
        model.addAttribute("product",productDto);
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("supplier",suppliers);
        return "update-product";
    }
}
