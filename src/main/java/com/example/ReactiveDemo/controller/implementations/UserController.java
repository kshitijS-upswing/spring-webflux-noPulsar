package com.example.ReactiveDemo.controller.implementations;

import com.example.ReactiveDemo.controller.requests.CreateUserRequest;
import com.example.ReactiveDemo.controller.DTO.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserController {
    Mono<UserDTO> createUser(@RequestBody CreateUserRequest request);
    Mono<UserDTO> getUserByEmail(@RequestParam String emailId);
    Mono<UserDTO> getUserById(@PathVariable UUID userId);
}
