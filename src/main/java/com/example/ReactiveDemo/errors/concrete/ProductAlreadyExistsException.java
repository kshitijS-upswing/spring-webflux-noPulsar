package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class ProductAlreadyExistsException extends GeneralException {
    public ProductAlreadyExistsException(){
        super("Product with same name already exists", "PRODUCT_ALREADY_EXISTS");
    }
}
