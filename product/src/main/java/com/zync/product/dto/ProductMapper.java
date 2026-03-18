package com.zync.product.dto;

import com.zync.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(CreateProductDto dto) {
        if (dto == null) return null;

        return ProductEntity.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .build();
    }
}