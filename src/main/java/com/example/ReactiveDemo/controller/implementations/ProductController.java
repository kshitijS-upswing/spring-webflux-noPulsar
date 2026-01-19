package com.example.ReactiveDemo.controller.implementations;

import com.example.ReactiveDemo.controller.requests.CreateProductRequest;
import com.example.ReactiveDemo.controller.DTO.ProductDTO;
import com.example.ReactiveDemo.controller.requests.UpdateStockRequest;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductController {
    Mono<ProductDTO> createProduct(@RequestBody CreateProductRequest request);
    Mono<ProductDTO> getProductById(UUID prodId);
    Flux<ProductDTO> getProducts();
    Mono<ProductDTO> updateProductQuantity(@RequestBody UpdateStockRequest request);
}
