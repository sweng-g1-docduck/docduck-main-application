package com.docduck.application.utils;

public class InvalidID extends Throwable {

    private String errorMessage;

    public InvalidID(String message) {
        this.errorMessage = message;
    }

    @Override
    public void printStackTrace() {
        System.err.println(errorMessage);
    }
}
