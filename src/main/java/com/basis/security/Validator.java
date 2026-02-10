package com.basis.security;

public abstract class Validator {
    protected static String errorMessage;

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        Validator.errorMessage = errorMessage;
    }
}
