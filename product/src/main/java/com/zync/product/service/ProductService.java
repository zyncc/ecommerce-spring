package com.zync.product.service;

import com.zync.product.dto.CreateProductDto;
import com.zync.product.dto.InventoryRequestDto;
import com.zync.product.dto.ProductMapper;
import com.zync.product.entity.ProductEntity;
import com.zync.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClient;
    private final ProductMapper productMapper;

    public List<ProductEntity> getAllProducts() {
       return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public void createProduct(CreateProductDto body) {
        ProductEntity entity = productMapper.toEntity(body);
        ProductEntity createdProduct = productRepository.save(entity);

        webClient.build().post()
                .uri("http://inventory-service/api/inventory")
                .bodyValue(new InventoryRequestDto(
                        createdProduct.getId(),
                        body.getQuantity()
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
