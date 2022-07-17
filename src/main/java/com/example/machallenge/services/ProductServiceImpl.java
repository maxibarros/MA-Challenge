package com.example.machallenge.services;

import com.example.machallenge.models.entities.Product;
import com.example.machallenge.repositories.BaseRepository;
import com.example.machallenge.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(BaseRepository<Product> baseRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Product> findByProductID(String productID) {
        return productRepository.findByProductID(productID);
    }

    @Override
    public Boolean existsByProductID(String productID) {
        return productRepository.existsByProductID(productID);
    }

    @Override
    @Transactional
    public void deleteByProductID(String productID) {
        productRepository.deleteByProductID(productID);
    }
}
