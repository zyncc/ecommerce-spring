package com.zync.product.controller;

import com.zync.product.dto.CreateProductDto;
import com.zync.product.entity.ProductEntity;
import com.zync.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProductDto body) {
        productService.createProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created product");
    }
}
