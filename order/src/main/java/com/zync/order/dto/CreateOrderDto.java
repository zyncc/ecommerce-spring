package com.zync.order.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderDto {
    private String address;
    private String city;
    private String state;
    private String pincode;
    private int quantity;
    private UUID productId;
}