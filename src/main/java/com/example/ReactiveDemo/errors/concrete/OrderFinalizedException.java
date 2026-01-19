package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class OrderFinalizedException extends GeneralException {
    public OrderFinalizedException() {
        super("Order has already been finalized", "ORDER_FINALIZED");
    }
}
