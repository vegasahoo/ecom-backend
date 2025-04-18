package com.satya.ecom.service;

import com.satya.ecom.dto.product.CreateProductDto;
import com.satya.ecom.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(UUID id);
    Product addProduct(CreateProductDto createProductRequest);
    Product updateProductById(UUID id, CreateProductDto createProductRequest);
    void deleteProductById(UUID id);
}
