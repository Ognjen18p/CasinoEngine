package com.application.security.email_service;

import static com.application.configuration.CasinoConfiguration.*;

import java.util.HashMap;

public class VerificatonService {

    private static VerificatonService instance;
    private static final HashMap<String, String> pendingVerifications = new HashMap<>();
    private static final HashMap<String, Long> tokenExpiry = new HashMap<>();
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public static VerificatonService getInstance() {
        if (instance == null) instance = new VerificatonService();
        return instance;
    }

    public String generateToken(String email) {
        String token = String.valueOf((int) (Math.random() * 900_000) + 100_000);
        pendingVerifications.put(email, token);
        Long expiryTime = System.currentTimeMillis() + TOKEN_EXPIRE_DURATION;
        tokenExpiry.put(email, expiryTime);
        return token;
    }

    public boolean verifyToken(String email, String token) {
        String existingPendingToken =  pendingVerifications.get(email);
        if(existingPendingToken == null){
            errorMessage = "Verification code does not exist";
            return false;
        }

        if(existingPendingToken.equals(token)) {
            if(System.currentTimeMillis() < tokenExpiry.get(email)) {
                pendingVerifications.remove(email);
                tokenExpiry.remove(email);
                return true;
            }
            errorMessage = "Verification code expired";
            pendingVerifications.remove(email);
            tokenExpiry.remove(email);
        }
        errorMessage = "Wrong code";
        return false;
    }

}
