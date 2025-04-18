package com.satya.ecom.dto.product;

import jakarta.validation.constraints.NotBlank;

public record CreateProductDto(@NotBlank(message = "name is required") String name,
                               @NotBlank(message = "description is required") String description,
                               @NotBlank(message = "price is required") Double price,
                               @NotBlank(message = "imageUrl is required") String imageUrl,
                               @NotBlank(message = "category is required") String category,
                               @NotBlank(message = "stockQuantity is required") Integer stockQuantity) {

}