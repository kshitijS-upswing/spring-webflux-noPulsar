package com.example.ReactiveDemo.controller.DTO;

import com.example.ReactiveDemo.repository.entities.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private UUID orderId;
    private UUID userId;
    private List<ProductOrderDTO> products;
    private OrderStatus status;
}
