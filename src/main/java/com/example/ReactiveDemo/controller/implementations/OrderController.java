package com.example.ReactiveDemo.controller;

import com.example.ReactiveDemo.controller.DTO.OrderDTO;
import com.example.ReactiveDemo.controller.requests.AddProductToOrderRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderController {

    Mono<OrderDTO> createOrder(UUID userId);

    Flux<OrderDTO> getOrders(UUID userId);

    Mono<OrderDTO> getOrder(UUID orderId, UUID userId);

    Mono<OrderDTO> addProductToOrder(
            UUID orderId,
            UUID userId,
            AddProductToOrderRequest request
    );

    Mono<OrderDTO> confirmOrder(UUID orderId, UUID userId);
}

