package com.docduck.application.xmldom;

public class IDAlreadyExists extends Throwable {

    public IDAlreadyExists() {

    }

    @Override
    public void printStackTrace() {
        System.err.println("The provided ID already exists in the data");
        System.out.println("Try getting the data for this id instead");
    }
}
