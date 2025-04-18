package com.satya.ecom.dto.cart;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CartItemRequestDto (@NotBlank(message = "Name is required") UUID productId,
                                  @NotBlank(message = "Password is required") Integer quantity) {
}
