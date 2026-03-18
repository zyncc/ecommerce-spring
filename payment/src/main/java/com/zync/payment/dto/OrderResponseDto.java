package com.zync.payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private UUID productId;

    @NotBlank(message = "Address is required")
    @Size(min = 30, max = 100, message = "Address must be 30-100 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 5, max = 30, message = "City must be 5-30 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(min = 5, max = 20, message = "State must be 5-20 characters")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Size(min = 6, max = 6, message = "Pincode must be 6 characters")
    private String pincode;

    @NotNull
    @Min(value = 1, message = "Quantity must be atleast 1")
    private int quantity;

    @NotNull(message = "paymentSuccess is required")
    private Boolean paymentSuccess;
}