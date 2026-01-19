package com.example.ReactiveDemo.repository.entities;

import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_PROGRESS(1),
    CONFIRMED(2),
    CANCELLED(3);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public static OrderStatus fromCode(int code) {
        return switch (code) {
            case 1 -> IN_PROGRESS;
            case 2 -> CONFIRMED;
            case 3 -> CANCELLED;
            default -> throw new IllegalArgumentException("Unknown status code: " + code);
        };
    }
}

