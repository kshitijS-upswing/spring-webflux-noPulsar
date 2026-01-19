package com.example.ReactiveDemo.service.implementations;

import com.example.ReactiveDemo.controller.DTO.UserDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<UserDTO> createUser(String email);
    Mono<UserDTO> findUserById(UUID userId);
    Mono<UserDTO> findUserByEmail(String email);
}
