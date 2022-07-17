package com.example.machallenge.mappers;

import com.example.machallenge.models.dto.ProductDTO;
import com.example.machallenge.models.dto.ProductPutRequestDTO;
import com.example.machallenge.models.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO mapProduct(Product product);

    Product mapProduct(ProductDTO productDTO);

    ProductPutRequestDTO mapProductPutRequest(Product product);

    Product mapProductPutRequest(ProductPutRequestDTO productPutRequestDTO);
}
