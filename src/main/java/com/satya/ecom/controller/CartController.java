package com.satya.ecom.controller;

import com.satya.ecom.dto.cart.CartItemRequestDto;
import com.satya.ecom.model.Cart;
import com.satya.ecom.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartService.addToCart(cartItemRequestDto);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> viewCart(){
        return ResponseEntity.ok(cartService.viewCart());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable (name = "id") String productId){
        Cart cart = cartService.removeFromCart(UUID.fromString(productId));
        return ResponseEntity.ok(cart);
    }
}
