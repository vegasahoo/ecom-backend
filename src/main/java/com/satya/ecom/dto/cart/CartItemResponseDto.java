package com.satya.ecom.dto.cart;

import com.satya.ecom.model.Product;

import java.util.UUID;

public record CartItemResponseDto(UUID id, Product product, int quantity) {
}
