package com.example.ReactiveDemo.service;

import com.example.ReactiveDemo.controller.DTO.ProductDTO;
import com.example.ReactiveDemo.errors.concrete.InsufficientStockException;
import com.example.ReactiveDemo.errors.concrete.ProductAlreadyExistsException;
import com.example.ReactiveDemo.errors.concrete.ProductNotFoundException;
import com.example.ReactiveDemo.repository.ProductRepository;
import com.example.ReactiveDemo.repository.entities.ProductEntity;
import com.example.ReactiveDemo.service.implementations.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    // Used for creation of product and addition to list of available products
    // Should ideally not allow addition of products with same name
    @Override
    public Mono<ProductDTO> createProduct(String productName, int productCost, int productQuantity) {
        return productRepo.existsByName(productName)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ProductAlreadyExistsException());
                    }

                    return productRepo.save(
                            ProductEntity.builder()
                                    .name(productName)
                                    .price(productCost)
                                    .availableQuantity(productQuantity)
                                    .build()
                    );
                })
                .map(product -> ProductDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productQuantity(product.getAvailableQuantity())
                        .build());
    }


    // Simpel get all function
    @Override
    public Flux<ProductDTO> getAllProducts() {
        return productRepo.findAll()
                .map(product -> ProductDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productQuantity(product.getAvailableQuantity())
                        .build());
    }

    // Find a product by a particular ID
    // Self explanatory
    @Override
    public Mono<ProductDTO> findProductById(UUID productId) {
        return productRepo.findById(productId)
                .map(product -> ProductDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productQuantity(product.getAvailableQuantity())
                        .build());
    }

    // Update the stock of a product using delta values only
    // Should ideally prevent negative quantity
    @Override
    public Mono<ProductDTO> updateStock(UUID productId, int delta) {
        return productRepo.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .flatMap(product -> {
                    int currentQty = product.getAvailableQuantity();
                    int newQty = currentQty + delta;

                    if (newQty < 0) {
                        return Mono.error(new InsufficientStockException());
                    }

                    product.setAvailableQuantity(newQty);
                    return productRepo.save(product);
                })
                .map(product -> ProductDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productQuantity(product.getAvailableQuantity())
                        .build());
    }
}
