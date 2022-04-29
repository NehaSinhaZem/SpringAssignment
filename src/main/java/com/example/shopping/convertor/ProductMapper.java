package com.example.shopping.convertor;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProductMapper {
    private static ModelMapper modelMapper = new ModelMapper();
    public ProductDto convertToDto(Product product){
        return modelMapper.map(product,ProductDto.class);
    }
    public Product convertToEntity(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }
    public List<ProductDto> getDtoList(List<Product> products){
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }
}
