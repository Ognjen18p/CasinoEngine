package com.basis.card;

import java.time.LocalDate;

public class Card {
    private int cardId;
    private int personId;
    private String cardLastFour;
    private String cardholderName;
    private LocalDate expiryDate;

    public Card(int personId, String cardNumber, String cardholderName, LocalDate expiryDate) {
        this.personId = personId;
        cardLastFour = cardNumber.substring(cardNumber.length() - 4);
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
    }

    public int getPersonId() {
        return personId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumberHash() {
        return cardLastFour;
    }

    public void setCardNumberHash(String cardLastFour) {
        this.cardLastFour = cardLastFour;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
