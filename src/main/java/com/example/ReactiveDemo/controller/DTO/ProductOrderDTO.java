package com.example.ReactiveDemo.controller.DTO;

import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {
    private UUID productId;
    private String productName;
    private int productPrice;
    private int productOrderQuantity;
}
