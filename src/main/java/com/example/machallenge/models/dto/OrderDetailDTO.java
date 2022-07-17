package com.example.machallenge.models.dto;

import com.example.machallenge.models.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class OrderDetailDTO {

    private ProductDTO product;

    private int quantity;
}
