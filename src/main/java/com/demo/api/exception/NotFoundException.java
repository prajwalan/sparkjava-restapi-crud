package com.demo.api.exception;

@SuppressWarnings("serial")
public class NotFoundException extends Exception {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}
