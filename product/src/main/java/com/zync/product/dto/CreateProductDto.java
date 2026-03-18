package com.zync.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 30, message = "Title must be 10-30 characters")
    private String title;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price should be minimum $1")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be minimum 1")
    private Integer quantity;
}