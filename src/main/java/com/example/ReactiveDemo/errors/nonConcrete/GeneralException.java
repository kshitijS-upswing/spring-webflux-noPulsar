package com.example.ReactiveDemo.errors.nonConcrete;

public abstract class GeneralException extends RuntimeException{

    private final String code;

    protected GeneralException(String message, String code){
        super(message, null, false, false);
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}