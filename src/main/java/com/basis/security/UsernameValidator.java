package com.basis.security;

public class UsernameValidator {
    private static final int MIN_USERNAME_LENGTH = 3;
    public static boolean validateUsername(String username){
        if(username.length() < MIN_USERNAME_LENGTH) return false;

        for(char ch : username.toCharArray()){
            if(!Character.isDigit(ch) || !Character.isAlphabetic(ch))
                return false;
        }
        return true;
    }
}
