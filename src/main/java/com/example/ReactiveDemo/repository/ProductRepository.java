package com.example.ReactiveDemo.repository;

import com.example.ReactiveDemo.repository.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository
        extends R2dbcRepository<ProductEntity, UUID> {

    Mono<ProductEntity> findByName(String name);

    Mono<Boolean> existsByName(String name);
}

