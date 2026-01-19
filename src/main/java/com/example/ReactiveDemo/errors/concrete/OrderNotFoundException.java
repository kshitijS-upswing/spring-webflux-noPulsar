package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class OrderNotFoundException extends GeneralException {
    public OrderNotFoundException(){
        super("Selected user does not have Order with provided order ID", "ORDER_NOT_FOUND");
    }
}
