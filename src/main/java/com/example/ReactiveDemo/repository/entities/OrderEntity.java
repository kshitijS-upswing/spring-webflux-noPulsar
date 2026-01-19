package com.example.ReactiveDemo.repository.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table("orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    // 🔥 This is what gets persisted
    @Column("status")
    private Integer statusCode;

    @Column("created_at")
    private OffsetDateTime createdAt;

    // 🧠 Domain-friendly accessors
    @Transient
    public OrderStatus getStatus() {
        return OrderStatus.fromCode(statusCode);
    }

    public void setStatus(OrderStatus status) {
        this.statusCode = status.getCode();
    }
}
