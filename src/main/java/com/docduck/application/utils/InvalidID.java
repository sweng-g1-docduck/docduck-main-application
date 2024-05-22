package com.docduck.application.utils;

public class InvalidID extends Throwable {

    private boolean isIDInvalid;

    public InvalidID(boolean idInvalid) {
        this.isIDInvalid = idInvalid;
    }

    public boolean isIDInvalid() {
        return isIDInvalid;
    }
}
