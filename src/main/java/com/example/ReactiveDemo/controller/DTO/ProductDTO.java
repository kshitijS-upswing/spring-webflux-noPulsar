package com.example.ReactiveDemo.controller.DTO;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private UUID productId;
    private String productName;
    private int productPrice;
    private int productQuantity;
}
