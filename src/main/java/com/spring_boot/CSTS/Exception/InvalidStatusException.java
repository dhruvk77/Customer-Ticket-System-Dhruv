package com.spring_boot.CSTS.Exception;

public class InvalidStatusException extends RuntimeException{
    public InvalidStatusException(String message){
        super(message);
    }
}
