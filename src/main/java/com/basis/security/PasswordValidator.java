package com.basis.security;

public class PasswordValidator {
    private static final int MIN_PASSWORD_LENGTH = 8;
    public static boolean isPasswordValid(String password){
        if(password.length() < MIN_PASSWORD_LENGTH) return false;

        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for(char ch : password.toCharArray()){
            if(Character.isWhitespace(ch)) return false;
            else if(Character.isDigit(ch)) hasDigit = true;
            else if(Character.isLowerCase(ch)) hasLower = true;
            else if(Character.isUpperCase(ch)) hasUpper = true;
            else hasSpecial = true;
        }
        return hasDigit && hasLower && hasUpper && hasSpecial;
    }
}
