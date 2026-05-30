package com.application.security;

import static com.application.configuration.CasinoConfiguration.*;

import java.util.HashMap;

public class LoginAttemptSecurity {

    private static LoginAttemptSecurity instance;

    private static final HashMap<String, Integer> loginAttempts = new HashMap<>();
    private static final HashMap<String, Long> lockedTimes = new HashMap<>();
    private String errorMessage;

    public static LoginAttemptSecurity getInstance() {
        if (instance == null) instance = new LoginAttemptSecurity();
        return instance;
    }

    public void recordLoginAttempt(String username) {
        int attempts = loginAttempts.getOrDefault(username, 0) + 1;
        loginAttempts.put(username, attempts);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            lockedTimes.put(username, System.currentTimeMillis() + LOCK_DURATION);
        }

        /// Save to DB out time! !!!!!!!!!! /////////
    }

    public boolean isLocked(String username) {
        if (!lockedTimes.containsKey(username)) return false;

        if (lockedTimes.get(username) < System.currentTimeMillis()) {
            loginAttempts.remove(username);
            lockedTimes.remove(username);
            return false;
        }
        errorMessage = "Your account is locked for " + (lockedTimes.get(username) - System.currentTimeMillis());
        return true;
    }

}
