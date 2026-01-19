package com.example.ReactiveDemo.controller;

import com.example.ReactiveDemo.controller.DTO.OrderDTO;
import com.example.ReactiveDemo.controller.requests.AddProductToOrderRequest;
import com.example.ReactiveDemo.service.implementations.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.ReactiveDemo.controller.OrderController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    // CREATE ORDER
    // POST /orders?userId=...
    @PostMapping
    @Override
    public Mono<OrderDTO> createOrder(@RequestParam UUID userId) {
        return orderService.createOrder(userId);
    }

    // GET ALL ORDERS
    // GET /orders?userId=...
    @GetMapping
    @Override
    public Flux<OrderDTO> getOrders(@RequestParam UUID userId) {
        return orderService.getOrdersByUserId(userId);
    }

    // GET SINGLE ORDER (WITH PRODUCTS)
    // GET /orders/{orderId}?userId=...
    @GetMapping("/{orderId}")
    @Override
    public Mono<OrderDTO> getOrder(
            @PathVariable UUID orderId,
            @RequestParam UUID userId
    ) {
        return orderService.getOrderByOrderId(userId, orderId);
    }

    // ADD PRODUCT
    // POST /orders/{orderId}/items?userId=...
    @PostMapping("/{orderId}/items")
    @Override
    public Mono<OrderDTO> addProductToOrder(
            @PathVariable UUID orderId,
            @RequestParam UUID userId,
            @RequestBody AddProductToOrderRequest request
    ) {
        return orderService.addProductToOrder(
                userId,
                orderId,
                request.getProductId(),
                request.getQuantity()
        );
    }

    // CONFIRM ORDER
    // POST /orders/{orderId}/confirm?userId=...
    @PostMapping("/{orderId}/confirm")
    @Override
    public Mono<OrderDTO> confirmOrder(
            @PathVariable UUID orderId,
            @RequestParam UUID userId
    ) {
        return orderService.finalizeOrder(userId, orderId);
    }
}
