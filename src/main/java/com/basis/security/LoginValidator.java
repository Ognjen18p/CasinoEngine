package com.basis.security;

public class LoginValidator {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public LoginValidator() {
    }

    public boolean findUsername(String username) {

        return false;
    }

    public boolean findPassword(String password) {
        return false;
    }

}
