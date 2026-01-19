package com.example.ReactiveDemo.repository;

import com.example.ReactiveDemo.repository.entities.OrderItemsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrderItemsRepository
        extends R2dbcRepository<OrderItemsEntity, UUID> {

    Flux<OrderItemsEntity> findByOrderId(UUID orderId);

    Flux<OrderItemsEntity> findByProductId(UUID productId);
}
