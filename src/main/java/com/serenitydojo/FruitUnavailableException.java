package com.serenitydojo;

public class FruitUnavailableException extends Exception{

    public FruitUnavailableException(String message){
        super(message);
    }

    public FruitUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
