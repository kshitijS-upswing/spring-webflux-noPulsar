package com.example.ReactiveDemo.service.implementations;

import com.example.ReactiveDemo.controller.DTO.OrderDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderService {
    Mono<OrderDTO> createOrder(UUID userId);
    Flux<OrderDTO> getOrdersByUserId(UUID userId);
    Mono<OrderDTO> getOrderByOrderId(UUID userId, UUID orderId);
    Mono<OrderDTO> finalizeOrder(UUID userId, UUID orderId);
    Mono<OrderDTO> addProductToOrder(UUID userId, UUID orderId, UUID productId, int quantity
    );

}
