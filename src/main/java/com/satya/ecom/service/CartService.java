package com.satya.ecom.service;

import com.satya.ecom.dto.cart.CartItemRequestDto;
import com.satya.ecom.model.Cart;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CartService {

    Cart addToCart(CartItemRequestDto cartItemRequestDto);
    Cart viewCart();
    Cart removeFromCart(UUID productId);
}
