package com.application.security;

import com.database.card.CardDAO;
import com.database.person.PlayerDAO;

import java.time.LocalDate;

public class CardValidator {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isNameValid(String name) {
        if (name.length() < 2) {
            errorMessage = "Must be at least 2 characters long";
            return false;
        }
        return true;
    }

    public boolean isNumberValid(String cardNumber) {
        if (cardNumber.isEmpty()) {
            errorMessage = "Enter the card number";
            return false;
        }
        if (!cardNumber.trim().matches("\\d{16}")) {
            errorMessage = "Invalid card number";
            return false;
        }
        return true;
    }

    public boolean isCvvValid(String cvv) {
        if (cvv.isEmpty()) {
            errorMessage = "The cvv is empty";
            return false;
        }
        if (!cvv.matches("\\d{3,4}")) {
            errorMessage = "Invalid cvv";
            return false;
        }
        return true;
    }

    public boolean isExpired(int month, int year) {
        if (month == 0 || year == 0) {
            errorMessage = "The expiry date is empty";
            return false;
        }
        LocalDate expiry = LocalDate.of(year, month, 1);
        if (LocalDate.now().isAfter(expiry)) {
            errorMessage = "The card is expired";
            return false;
        }
        return true;
    }
}
