package com.docduck.application.xmldom;

public class JDOMDataHandlerNotInitialised extends Throwable {

    public JDOMDataHandlerNotInitialised() {

    }

    public void printError() {
        System.err.println("XMLJDOMDataHandler instance is null, have you created an instance?");
    }
}
