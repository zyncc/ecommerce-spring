package com.zync.order.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderDto {
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

    private UUID productId;
}