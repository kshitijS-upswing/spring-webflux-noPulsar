package com.example.ReactiveDemo.controller.DTO;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private UUID id;
    private String email;
}
