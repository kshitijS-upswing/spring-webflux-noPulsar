package com.example.ReactiveDemo.errors.concrete;

import com.example.ReactiveDemo.errors.nonConcrete.GeneralException;

public class UserNotFoundException extends GeneralException {
    public UserNotFoundException(){
        super("User does not exist with given credentials", "USER_NOT_FOUND");
    }
}
