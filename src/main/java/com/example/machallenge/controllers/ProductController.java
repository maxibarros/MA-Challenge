package com.example.machallenge.controllers;

import com.example.machallenge.mappers.productos.ProductMapper;
import com.example.machallenge.mappers.productos.dto.ProductDTO;
import com.example.machallenge.mappers.productos.dto.ProductPutRequestDTO;
import com.example.machallenge.models.Product;
import com.example.machallenge.repositories.BaseRepository;
import com.example.machallenge.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductController extends BaseController<Product> {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    public ProductController(BaseRepository<Product> baseRepository) {
        super(baseRepository);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<Product> productList = super.list();
            if (productList.isEmpty()) {
                responseMap.put("success", Boolean.FALSE);
                responseMap.put("message", "No se encontraron productos.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            List<ProductDTO> productDTOList = productList
                    .stream()
                    .map(productMapper::mapProduct)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(productDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "{productID}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "productID") String productID) {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            Optional<Product> product = productService.findByProductID(productID);
            if (!product.isPresent()) {
                responseMap.put("error", "Producto no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
            }
            ProductDTO response = productMapper.mapProduct(product.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            if (result.hasErrors()) {
                responseMap.put("success", Boolean.FALSE);
                responseMap.put("validations", super.validate(result));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
            Product save = productService.save(productMapper.mapProduct(productDTO));
            ProductDTO response = productMapper.mapProduct(save);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "productID") String productID) {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            if (!productService.existsByProductID(productID)) {
                responseMap.put("error", "Producto no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
            }
            productService.deleteByProductID(productID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "{productID}")
    public ResponseEntity<?> saveProduct(@PathVariable(name = "productID") String productID,
                                         @Valid @RequestBody ProductPutRequestDTO productPutRequestDTO,
                                         BindingResult result) {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            Optional<Product> product = productService.findByProductID(productID);
            if (!product.isPresent()) {
                responseMap.put("error", "Producto no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
            }
            if (result.hasErrors()) {
                responseMap.put("success", Boolean.FALSE);
                responseMap.put("validations", super.validate(result));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            product.get().setName(productPutRequestDTO.getNombre());
            product.get().setLongDescription(productPutRequestDTO.getDescripcionLarga());
            product.get().setShortDescription(productPutRequestDTO.getDescripcionCorta());
            product.get().setUnitPrice(productPutRequestDTO.getPrecioUnitario());
            productService.update(product.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
