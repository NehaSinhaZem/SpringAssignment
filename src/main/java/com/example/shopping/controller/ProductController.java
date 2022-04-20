package com.example.shopping.controller;

import com.example.shopping.entity.Product;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/list")
    public String getProducts(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_SUPPLIER"))) {
            int id=supplierService.findSupplierId(auth.getName());
            System.out.println(id);
            productList = service.getProductsBySupID(id);
        }
        else
            productList = service.getProducts();
        model.addAttribute("products",productList);
        return "list-products";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        Product product = new Product();
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("product",product);
        model.addAttribute("supplier",suppliers);
        return "add-product";
    }
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product){
        service.saveProduct(product);
        return "redirect:/products/list";
    }
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") int id, Model model){
        Product product = service.findProduct(id);
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
}
