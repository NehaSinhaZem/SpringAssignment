package com.example.shopping.controller;

import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Supplier;
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
    @GetMapping("/list")
    public String getSuppliers(Model model){
        List<SupplierDto> dtoList= service.getSuppliers();
        model.addAttribute("suppliers",dtoList);
        return "list-suppliers";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute("supplier",supplier);
        return "add-supplier";
    }
    @PostMapping(path = "/supplier")
    public String saveSupplier(@ModelAttribute("product") SupplierDto dto){
        Supplier supplier = service.saveSupplier(dto);
        return "redirect:/suppliers/list";
    }
    @PostMapping(path = "/supplier/{id}")
    public String updateSupplier(@PathVariable int id, SupplierDto dto){
        int res = service.updateSupplier(id,dto);
        return "redirect:/suppliers/list";
    }
    @GetMapping(path = "/supplier/update")
    public String updateSupplierForm(@RequestParam("id") int id,Model model){
        model.addAttribute("supplier",service.findSupplier(id));
        return "update-supplier";
    }
    @GetMapping("/delete")
    public String deleteSupplier(@RequestParam("id") int id){
        service.deleteSupplier(id);
        return "redirect:/suppliers/list";
    }
}
