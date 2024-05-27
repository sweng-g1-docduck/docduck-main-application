package com.docduck.application.xmldom;

public class ElementDataNotRemoved extends Throwable {

    private final String errorMessage;
    public ElementDataNotRemoved(String s) {
        errorMessage = s;
    }

    public void printErrorMessage() {
        System.out.println(errorMessage);
    }
}
