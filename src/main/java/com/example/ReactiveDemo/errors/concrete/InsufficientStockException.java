package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class InsufficientStockException extends GeneralException {
    public InsufficientStockException(){
        super("Product has insufficient stock", "INSUFFICIENT_STOCK");
    }
}
