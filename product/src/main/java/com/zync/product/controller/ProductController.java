package com.zync.product.controller;

import com.zync.product.dto.CreateProductDto;
import com.zync.product.entity.ProductEntity;
import com.zync.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductEntity> getProducts() {
        try {
            return productService.getAllProducts();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable UUID id) {
        try {
            Optional<ProductEntity> findProduct = productService.getProductById(id);
            return findProduct.orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductDto body) {
       try {
           productService.createProduct(body);
           return "Created product";
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }
}
