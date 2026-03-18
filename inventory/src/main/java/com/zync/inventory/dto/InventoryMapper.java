package com.zync.inventory.dto;

import com.zync.inventory.entity.InventoryEntity;

public class InventoryMapper {

    public static InventoryDto toDto(InventoryEntity entity) {
        if (entity == null) return null;

        return InventoryDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .productId(entity.getProductId())
                .build();
    }

    public static InventoryEntity toEntity(InventoryDto dto) {
        if (dto == null) return null;

        return InventoryEntity.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .productId(dto.getProductId())
                .build();
    }
}