package com.docduck.application.utils;

public class JDOMDataHandlerNotInitialised extends Throwable {

    public JDOMDataHandlerNotInitialised() {

    }

    @Override
    public void printStackTrace() {
        System.err.println("XMLJDOMDataHandler instance is null, have you created an instance?");
    }
}
