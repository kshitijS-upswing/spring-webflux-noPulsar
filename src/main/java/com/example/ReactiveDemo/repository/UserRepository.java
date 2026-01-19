package com.example.ReactiveDemo.repository;

import com.example.ReactiveDemo.repository.entities.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository
        extends R2dbcRepository<UserEntity, UUID> {

    Mono<UserEntity> findByEmail(String email);
}
