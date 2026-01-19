package com.example.ReactiveDemo.repository.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class ProductEntity {

    @Id
    private UUID id;

    private String name;

    private int price;

    @Column("available_quantity")
    private int availableQuantity;
}

