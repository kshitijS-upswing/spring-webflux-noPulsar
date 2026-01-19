package com.example.ReactiveDemo.repository;

import com.example.ReactiveDemo.repository.entities.OrderEntity;
import com.example.ReactiveDemo.repository.entities.OrderStatus;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrderRepository
        extends R2dbcRepository<OrderEntity, UUID> {

    Flux<OrderEntity> findByUserId(UUID userId);

    Flux<OrderEntity> findByStatus(OrderStatus status);
}

