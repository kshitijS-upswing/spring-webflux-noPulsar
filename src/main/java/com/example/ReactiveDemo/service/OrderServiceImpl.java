package com.example.ReactiveDemo.service;

import com.example.ReactiveDemo.controller.DTO.OrderDTO;
import com.example.ReactiveDemo.controller.DTO.ProductOrderDTO;
import com.example.ReactiveDemo.repository.OrderItemsRepository;
import com.example.ReactiveDemo.repository.OrderRepository;
import com.example.ReactiveDemo.repository.ProductRepository;
import com.example.ReactiveDemo.repository.UserRepository;
import com.example.ReactiveDemo.repository.entities.OrderEntity;
import com.example.ReactiveDemo.repository.entities.OrderItemsEntity;
import com.example.ReactiveDemo.repository.entities.OrderStatus;
import com.example.ReactiveDemo.repository.entities.ProductEntity;
import com.example.ReactiveDemo.service.implementations.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final OrderItemsRepository orderItemsRepo;
    private final ProductRepository productRepo;

    // --------------------
    // CREATE ORDER
    // --------------------
    @Override
    public Mono<OrderDTO> createOrder(UUID userId) {
        OrderEntity entity = OrderEntity.builder()
                .userId(userId)
                .statusCode(OrderStatus.IN_PROGRESS.getCode())
                .build();

        return orderRepo.save(entity)
                .map(order -> OrderDTO.builder()
                        .orderId(order.getId())
                        .userId(order.getUserId())
                        .status(order.getStatus())
                        .products(List.of())
                        .build());
    }


    // --------------------
    // GET ORDERS BY USER
    // --------------------
    @Override
    public Flux<OrderDTO> getOrdersByUserId(UUID userId) {
        return orderRepo.findByUserId(userId)
                .flatMap(this::toDTOWithProducts);
    }

    // --------------------
    // GET SINGLE ORDER
    // --------------------
    @Override
    public Mono<OrderDTO> getOrderByOrderId(UUID userId, UUID orderId) {
        return orderRepo.findById(orderId)
                .filter(order -> order.getUserId().equals(userId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Order not found")))
                .flatMap(this::toDTOWithProducts);
    }

    // --------------------
    // FINALIZE ORDER
    // --------------------
    @Override
    public Mono<OrderDTO> finalizeOrder(UUID userId, UUID orderId) {
        return orderRepo.findById(orderId)
                .filter(order -> order.getUserId().equals(userId))
                .switchIfEmpty(Mono.error(new IllegalStateException("Order not found")))
                .flatMap(order -> {

                    if (order.getStatus() != OrderStatus.IN_PROGRESS) {
                        return Mono.error(new IllegalStateException("Order already finalized"));
                    }

                    // 1️⃣ Fetch all order items
                    return orderItemsRepo.findByOrderId(order.getId())
                            // 2️⃣ Validate & reduce stock per item
                            .flatMap(this::validateAndReduceStock)
                            // 3️⃣ Only after ALL stock updates succeed
                            .then(Mono.defer(() -> {
                                order.setStatus(OrderStatus.CONFIRMED);
                                return orderRepo.save(order);
                            }))
                            // 4️⃣ Return full DTO
                            .flatMap(this::toDTOWithProducts);
                });
    }



    // --------------------
    // VALIDATE STOCK
    // --------------------
    private Mono<Void> validateStock(OrderItemsEntity item) {
        return productRepo.findById(item.getProductId())
                .flatMap(product -> {
                    if (product.getAvailableQuantity() < item.getQuantity()) {
                        return Mono.error(new IllegalStateException("Insufficient stock"));
                    }

                    product.setAvailableQuantity(
                            product.getAvailableQuantity() - item.getQuantity()
                    );
                    return productRepo.save(product).then();
                });
    }

    private Mono<Void> validateAndReduceStock(OrderItemsEntity item) {
        return productRepo.findById(item.getProductId())
                .switchIfEmpty(Mono.error(new IllegalStateException("Product not found")))
                .flatMap(product -> {

                    if (product.getAvailableQuantity() < item.getQuantity()) {
                        return Mono.error(
                                new IllegalStateException(
                                        "Insufficient stock for product " + product.getId()
                                )
                        );
                    }

                    product.setAvailableQuantity(
                            product.getAvailableQuantity() - item.getQuantity()
                    );

                    return productRepo.save(product).then();
                });
    }


    private Mono<OrderEntity> updateOrderStatus(OrderEntity order, OrderStatus status) {
        order.setStatus(status);
        return orderRepo.save(order);
    }

    // --------------------
    // MAPPERS
    // --------------------
    private Mono<OrderDTO> toDTOWithProducts(OrderEntity order) {
        return orderItemsRepo.findByOrderId(order.getId())
                .flatMap(item ->
                        productRepo.findById(item.getProductId())
                                .map(product -> ProductOrderDTO.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .productPrice(item.getPriceAtOrderTime())
                                        .productOrderQuantity(item.getQuantity())
                                        .build())
                )
                .collectList()
                .map(products -> OrderDTO.builder()
                        .orderId(order.getId())
                        .userId(order.getUserId())
                        .status(order.getStatus())
                        .products(products)
                        .build());
    }

    private OrderDTO toDTOWithoutProducts(OrderEntity order) {
        return OrderDTO.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .products(List.of())
                .build();
    }

    @Override
    public Mono<OrderDTO> addProductToOrder(
            UUID userId,
            UUID orderId,
            UUID productId,
            int quantity
    ) {
        return orderRepo.findById(orderId)
                .filter(order -> order.getUserId().equals(userId))
                .switchIfEmpty(Mono.error(new IllegalStateException("Order not found")))
                .flatMap(order -> {
                    if (order.getStatus() != OrderStatus.IN_PROGRESS) {
                        return Mono.error(new IllegalStateException("Cannot modify finalized order"));
                    }

                    return productRepo.findById(productId)
                            .switchIfEmpty(Mono.error(new IllegalStateException("Product not found")))
                            .flatMap(product -> {

                                OrderItemsEntity item = OrderItemsEntity.builder()
                                        .orderId(order.getId())
                                        .productId(product.getId())
                                        .quantity(quantity)
                                        // 🔒 snapshot price at order time
                                        .priceAtOrderTime(product.getPrice())
                                        .build();

                                return orderItemsRepo.save(item);
                            })
                            .then(toDTOWithProducts(order));
                });
    }

}
