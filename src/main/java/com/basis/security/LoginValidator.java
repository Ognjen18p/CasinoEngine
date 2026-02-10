package com.basis.security;

public class LoginValidator extends Validator {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public static String getErrorMessage() {
        return errorMessage;
    }

    public LoginValidator() {
    }

    public static boolean findUsername(String username) {
        return false;
    }

    public static boolean findPassword(String password) {
        return false;
    }

}
