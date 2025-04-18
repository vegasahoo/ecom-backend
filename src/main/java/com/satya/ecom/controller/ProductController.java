package com.satya.ecom.controller;

import com.satya.ecom.dto.product.CreateProductDto;
import com.satya.ecom.model.Product;
import com.satya.ecom.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable (name = "id") String productId){
        Product product = productService.getProductById(UUID.fromString(productId));
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductDto createProductRequest){
        Product product = productService.addProduct(createProductRequest);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable (name = "id") String productId, @RequestBody CreateProductDto createProductRequest){
        Product product = productService.updateProductById(UUID.fromString(productId), createProductRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable (name = "id") String productId){
        productService.deleteProductById(UUID.fromString(productId));
        return ResponseEntity.ok("Product deleted for id: "+productId);
    }
}
