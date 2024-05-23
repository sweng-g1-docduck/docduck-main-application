package com.docduck.application.xmldom;

public class InvalidID extends Throwable {

    private final String errorMessage;

    public InvalidID(String message) {
        this.errorMessage = message;
    }

    @Override
    public void printStackTrace() {
        System.err.println(errorMessage);
    }
}
