package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class EmailAlreadyInUseException extends GeneralException {
    public EmailAlreadyInUseException(){
        super("Provided email is already in use", "EMAIL_ALREADY_USED");
    }
}
