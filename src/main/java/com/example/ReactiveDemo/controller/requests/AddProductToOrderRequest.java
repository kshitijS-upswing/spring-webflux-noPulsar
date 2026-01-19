package com.example.ReactiveDemo.controller.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddProductToOrderRequest {
    private UUID productId;
    private int quantity;
}
