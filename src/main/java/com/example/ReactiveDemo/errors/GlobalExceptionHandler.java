package com.example.ReactiveDemo.errors;

import com.example.ReactiveDemo.errors.nonConcrete.ApiError;
import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(GeneralException.class)
    public Mono<ResponseEntity<ApiError>> handleException(GeneralException exception){
        log.warn("New exception: " + exception.getCode() + " with message: " + exception.getMessage());

        return Mono.just(
                ResponseEntity.badRequest().
                        body(new ApiError(exception.getCode(), exception.getMessage()))
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiError>> handleUnexpectedException(Exception ex){
        log.error("Unexpected exception: " + ex.getMessage());

        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiError(
                                "INTERNAL_SERVER_ERROR",
                                "Something went wrong"
                        ))
        );
    }
}
