package com.example.shopping.convertor;

import com.example.shopping.dto.SupplierDto;
import com.example.shopping.entity.Supplier;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierMapper {
    private static ModelMapper modelMapper = new ModelMapper();
    public SupplierDto convertToDto(Supplier supplier){
        return modelMapper.map(supplier, SupplierDto.class);
    }
    public Supplier convertToEntity(SupplierDto supplierDto){
        return modelMapper.map(supplierDto, Supplier.class);
    }
    public List<SupplierDto> getDtoList(List<Supplier> suppliers){
        return suppliers.stream().map(supplier -> modelMapper.map(supplier, SupplierDto.class))
                .collect(Collectors.toList());
    }
}
