package com.satya.ecom.service.impl;

import com.satya.ecom.dto.product.CreateProductDto;
import com.satya.ecom.exception.ResourceNotFoundException;
import com.satya.ecom.mapper.ProductMapper;
import com.satya.ecom.model.Product;
import com.satya.ecom.repository.ProductRepo;
import com.satya.ecom.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product addProduct(CreateProductDto createProductRequest) {
        Product product = ProductMapper.mapCreateProductDtoToProduct(createProductRequest);
        return productRepo.save(product);
    }

    @Override
    public Product updateProductById(UUID id, CreateProductDto createProductRequest) {
        Product product = productRepo.findById(id).
                orElseThrow(() ->
                    new ResourceNotFoundException("Product not found with id: " + id));
        product.setName(createProductRequest.name());
        product.setDescription(createProductRequest.description());
        product.setStockQuantity(createProductRequest.stockQuantity());
        product.setImageUrl(createProductRequest.imageUrl());
        product.setCategory(createProductRequest.category());
        product.setPrice(createProductRequest.price());
        return productRepo.save(product);
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepo.deleteById(id);
    }
}
