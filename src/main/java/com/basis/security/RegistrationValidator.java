package com.basis.security;

public class RegistrationValidator {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private String errorMessage;

    public boolean isNameValid(String name) {
        return true;
    }

    public boolean isSurnameValid(String surname) {
        return true;
    }

    public boolean isEmailValid(String email) {
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean doPasswordMatch(String password, String confirmPassword) {
        return true;
    }

    public boolean isUsernameValid(String username) {
        if (username.length() < MIN_USERNAME_LENGTH) return false;

        boolean hasLetter = false;
        for (char ch : username.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                hasLetter = true;
                continue;
            }
            if (Character.isDigit(ch))
                continue;

            errorMessage = "Username must have only letters and digits";
            return false;
        }

        return hasLetter;
    }

    public boolean isPasswordValid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) return false;

        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isWhitespace(ch)) return false;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isUpperCase(ch)) hasUpper = true;
            else hasSpecial = true;
        }

        if (!hasDigit)
            errorMessage = "Password must have at least one digit";
        if (!hasLower)
            errorMessage = "Password must have at least one lowercase letter";
        if (!hasUpper)
            errorMessage = "Password must have at least one uppercase letter";
        if (!hasSpecial)
            errorMessage = "Password must have at least one special character";

        return hasDigit && hasLower && hasUpper && hasSpecial;
    }
}
