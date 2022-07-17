package com.example.machallenge.services;

import com.example.machallenge.models.entities.Product;

import java.util.Optional;

public interface ProductService extends BaseService<Product> {

    Optional<Product> findByProductID(String productID);

    Boolean existsByProductID(String productID);

    void deleteByProductID(String productID);
}
