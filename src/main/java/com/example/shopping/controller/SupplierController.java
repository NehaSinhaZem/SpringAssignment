package com.example.shopping.controller;

import com.example.shopping.entity.Supplier;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
@AllArgsConstructor
@NoArgsConstructor
public class SupplierController {
    List<Supplier> suppliers;
    @Autowired
    private SupplierService service;
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public String getSuppliers(Model model){
        suppliers = service.getSuppliers();
        model.addAttribute("suppliers",suppliers);
        return "list-suppliers";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute("supplier",supplier);
        return "add-supplier";
    }
    @PostMapping("/save")
    public String saveSupplier(@ModelAttribute("supplier") Supplier supplier){
        service.saveSupplier(supplier);
        return "redirect:/suppliers/list";
    }
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") int id, Model model){
        Supplier supplier = service.findSupplier(id);
        model.addAttribute("supplier",supplier);
        return "add-supplier";
    }
    @GetMapping("/delete")
    public String deleteSupplier(@RequestParam("id") int id){
        Long r = productService.deleteBySupID(id);
        service.deleteSupplier(id);
        return "redirect:/suppliers/list";
    }
}
