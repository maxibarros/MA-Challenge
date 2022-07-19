package com.example.machallenge.mappers.productos;

import com.example.machallenge.mappers.productos.dto.ProductDTO;
import com.example.machallenge.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO mapProduct(Product product) {
        if(product == null) return null;
        return new ProductDTO(product.getProductID(), product.getName(),
                product.getShortDescription(), product.getLongDescription(), product.getUnitPrice());
    }

    public Product mapProduct(ProductDTO productDTO) {
        if(productDTO == null) return null;
        return new Product(productDTO.getId(), productDTO.getNombre(),
                productDTO.getDescripcionCorta(), productDTO.getDescripcionLarga(), productDTO.getPrecioUnitario());
    }
}
