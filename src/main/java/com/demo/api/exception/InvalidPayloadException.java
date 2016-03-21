package com.demo.api.exception;

@SuppressWarnings("serial")
public class InvalidPayloadException extends Exception {

    public InvalidPayloadException() {
        super();
    }

    public InvalidPayloadException(String message) {
        super(message);
    }

}
