package com.example.shopping.controller;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Product;
import com.example.shopping.exception.ProductNotFoundException;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/add")
    public String showAddForm(Model model){
        Product product = new Product();
        List<SupplierDto> suppliers = supplierService.getSuppliers();
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
        List<ProductDto> dtoList = service.getProducts();
        model.addAttribute("products",dtoList);
        return "list-products";
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id){
        ProductDto productDto = service.findProduct(id);
        if(productDto==null)
            throw new ProductNotFoundException("Product not found");
        return ResponseEntity.ok().body(productDto);
    }
    @PostMapping(path = "/product")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult){
            // convert DTO to entity ,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
        if(!bindingResult.hasErrors()) {
            service.saveProduct(productDto);
            return "redirect:/products/list";
        }
//        else
            return null;
    }
    @PostMapping(path = "/product/{id}")
    public String updateProduct(@PathVariable int id, ProductDto productDto){
        int res = service.updateProduct(id,productDto);

        return "redirect:/products/list";
    }
    @GetMapping(path = "/product/update")
    public String updateProductForm(@RequestParam("id") int id,Model model){
        ProductDto productDto=service.findProduct(id);
        model.addAttribute("product",productDto);
        List<SupplierDto> suppliers = supplierService.getSuppliers();
        model.addAttribute("supplier",suppliers);
        return "update-product";
    }
}
