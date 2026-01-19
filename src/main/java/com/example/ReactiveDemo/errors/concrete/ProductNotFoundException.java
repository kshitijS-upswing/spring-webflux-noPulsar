package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class ProductNotFoundException extends GeneralException {
    public ProductNotFoundException(){
        super("Product does not exist", "PRODUCT_NOT_FOUND");
    }
}