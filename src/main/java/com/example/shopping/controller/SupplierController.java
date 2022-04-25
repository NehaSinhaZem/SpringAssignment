package com.example.shopping.controller;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Supplier;
import com.example.shopping.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/suppliers")
@AllArgsConstructor
@NoArgsConstructor
public class SupplierController {
    List<Supplier> suppliers;
    @Autowired
    private SupplierService service;
    @Autowired
    public ModelMapper modelMapper;
    @GetMapping("/list")
    public String getSuppliers(Model model){
        List<SupplierDto> dtoList= service.getSuppliers().stream().map(post -> modelMapper.map(post, SupplierDto.class))
                .collect(Collectors.toList());
        model.addAttribute("suppliers",dtoList);
        return "list-suppliers";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute("supplier",supplier);
        return "add-supplier";
    }
    @PostMapping(path = "/supplier",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveSupplier(SupplierDto dto){
        Supplier postRequest = modelMapper.map(dto, Supplier.class);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Supplier post = service.saveSupplier(postRequest);

        // convert entity to DTO
        ProductDto postResponse = modelMapper.map(post, ProductDto.class);
        return "redirect:/suppliers/list";
    }
    @PostMapping(path = "/supplier/{id}")
    public String updateSupplier(@PathVariable int id, SupplierDto dto){
        Supplier supplier = modelMapper.map(dto, Supplier.class);
        int res = service.updateSupplier(id,supplier);
        return "redirect:/suppliers/list";
    }
    @RequestMapping(path = "/supplier/update")
    public String updateSupplierForm(@RequestParam("id") int id,Model model){
        SupplierDto supplierDto = modelMapper.map(service.findSupplier(id),SupplierDto.class);
        model.addAttribute("supplier",supplierDto);
        return "update-supplier";
    }
    @GetMapping("/delete")
    public String deleteSupplier(@RequestParam("id") int id){
        service.deleteSupplier(id);
        return "redirect:/suppliers/list";
    }
}
