package com.hamdanas.exception;

@SuppressWarnings("serial")
public class InvalidTokenException extends Exception {
    public InvalidTokenException(){
        super();
    }

    public InvalidTokenException(String message){
        super(message);
    }
}
