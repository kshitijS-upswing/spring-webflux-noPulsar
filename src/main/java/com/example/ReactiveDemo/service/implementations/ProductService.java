package com.example.ReactiveDemo.service.implementations;

import com.example.ReactiveDemo.controller.DTO.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductService {
    Mono<ProductDTO> createProduct(String productName, int productCost, int productQuantity);
    Flux<ProductDTO> getAllProducts();
    Mono<ProductDTO> findProductById(UUID productId);
    Mono<ProductDTO> updateStock(UUID productId, int delta);
}
