package com.zync.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private int quantity;
    private Boolean paymentSuccess;
    private UUID productId;
    private Instant createdAt;
}