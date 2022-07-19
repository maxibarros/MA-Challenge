package com.example.machallenge.repositories;

import com.example.machallenge.models.Product;

import java.util.Optional;

public interface ProductRepository extends BaseRepository<Product> {

    Optional<Product> findByProductID(String productID);

    Boolean existsByProductID(String productID);

    void deleteByProductID(String productID);
}
