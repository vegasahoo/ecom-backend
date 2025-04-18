package com.satya.ecom.repository;

import com.satya.ecom.model.Cart;
import com.satya.ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
    CartItem findByCart(Cart cart);
}
