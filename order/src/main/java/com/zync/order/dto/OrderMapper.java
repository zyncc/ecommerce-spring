package com.zync.order.dto;

import com.zync.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(CreateOrderDto dto) {
        if (dto == null) return null;

        return OrderEntity.builder()
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .quantity(dto.getQuantity())
                .productId(dto.getProductId())
                .paymentSuccess(false)
                .build();
    }

    public OrderResponseDto toDto(OrderEntity entity) {
        if (entity == null) return null;

        return OrderResponseDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .city(entity.getCity())
                .state(entity.getState())
                .pincode(entity.getPincode())
                .quantity(entity.getQuantity())
                .paymentSuccess(entity.getPaymentSuccess())
                .productId(entity.getProductId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}