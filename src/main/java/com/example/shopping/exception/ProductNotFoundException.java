package com.example.shopping.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String msg){
        super(msg);
    }
    public ProductNotFoundException(Throwable throwable){
        super(throwable);
    }
    public ProductNotFoundException(String msg, Throwable throwable){
        super(msg,throwable);
    }
}
