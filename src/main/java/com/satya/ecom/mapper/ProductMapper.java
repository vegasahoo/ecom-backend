package com.satya.ecom.mapper;

import com.satya.ecom.dto.product.CreateProductDto;
import com.satya.ecom.model.Product;

public class ProductMapper {
    public static Product mapCreateProductDtoToProduct(CreateProductDto createProductDto){
        Product product = new Product();
        product.setName(createProductDto.name());
        product.setCategory(createProductDto.category());
        product.setPrice(createProductDto.price());
        product.setDescription(createProductDto.description());
        product.setImageUrl(createProductDto.imageUrl());
        product.setStockQuantity(createProductDto.stockQuantity());
        return product;
    }
}
