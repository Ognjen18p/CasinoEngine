package com.controller.security;

import java.time.LocalDate;
import java.time.Period;
import static com.application.configuration.CasinoConfiguration.*;

public class InputValidator {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isNameValid(String name) {
        if(name.length() < MIN_NAME_LENGTH) {
            errorMessage = "Name must have at least " + MIN_NAME_LENGTH + " letters";
            return false;
        }
        if(name.length() > MAX_NAME_LENGTH) {
            errorMessage = "Name cannot contain more then " + MAX_NAME_LENGTH + " letters";
            return false;
        }
        if(!name.matches("^[A-Za-z]+$")) {
            errorMessage = "Name may have only letters";
            return false;
        }
        return true;
    }

    public boolean isLastnameValid(String lastname) {
        if(lastname.length() < MIN_LASTNAME_LENGTH) {
            errorMessage = "Lastname must have at least " + MIN_LASTNAME_LENGTH + " letters";
            return false;
        }
        if(lastname.length() > MAX_LASTNAME_LENGTH) {
            errorMessage = "Lastname cannot contain more then " + MAX_LASTNAME_LENGTH + " letters";
            return false;
        }
        if(!lastname.matches("^[A-Za-z]+$")) {
            errorMessage = "Lastname may have only letters";
            return false;
        }
        return true;
    }

    public boolean isUsernameValid(String username) {
        if (username.length() < MIN_USERNAME_LENGTH) {
            errorMessage = "Username must have at least " + MIN_USERNAME_LENGTH + " letters";
            return false;
        }
        if(!username.matches("^[a-zA-Z0-9_]+")) {
            errorMessage = "Username may have only letters, digits or _";
            return false;
        }
        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errorMessage = "Password must have at least " + MIN_PASSWORD_LENGTH + " letters";
            return false;
        }

        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                errorMessage = "Password cannot contain whitespaces";
                return false;
            }
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

    public boolean arePasswordsEqual(String password, String confirmPassword) {
        if(password.equals(confirmPassword)) return true;
        errorMessage = "Passwords do not match";
        return false;
    }

    public boolean isEmailValid(String email) {
        if(!email.matches("^[A-Za-z0-9._-]+@[A-Za-z.-]+\\.[a-z]+$")){
            errorMessage = "Invalid email address";
            return false;
        }
        return true;
    }

    public boolean isAgeValid(LocalDate date) {
        if(date == null) {
            errorMessage = "You must enter your birth date";
            return false;
        }
        if(Period.between(date, LocalDate.now()).getYears() < 18) {
            errorMessage = "You must be at least 18 years old";
            return false;
        }
        return true;
    }

}
