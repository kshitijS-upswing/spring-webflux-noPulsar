package com.example.ReactiveDemo.errors.nonConcrete;

public record ApiError(
     String code,
     String message
) {}