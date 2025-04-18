package com.satya.ecom.repository;

import com.satya.ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {

}
