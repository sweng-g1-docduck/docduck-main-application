package com.docduck.application.gui;

public class FileNotFound extends Throwable{

    private final String errorMessage;

    public FileNotFound(String message) {
        this.errorMessage = message;
    }
    public void printErrorMessage() {
        System.err.println(errorMessage);
    }
}
