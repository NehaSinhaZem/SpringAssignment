package com.example.shopping.controller;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.service.CartService;
import com.example.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService service;
    @Autowired
    ProductService productService;
    @GetMapping("/add")
    public String addToCart(@RequestParam("id") int id){
        ProductDto productDto = productService.findProduct(id);
        service.addProduct(productDto);
        return "redirect:/cart/list";
    }
    @GetMapping("/remove")
    public String removeFromCart(@RequestParam("id") int id){
        service.removeProduct(id);
        return "redirect:/cart/list";
    }
    @GetMapping("/list")
    public String viewCart(Model model){
        model.addAttribute("products",service.getProducts());
        return "cart";
    }
}
