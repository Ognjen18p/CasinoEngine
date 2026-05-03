package com.basis.person.data;

import java.time.LocalDate;

public class DebitCard {
    private int cardId;
    private String cardNumberHash;
    private String cardholderName;
    private LocalDate expiryDate;

    public DebitCard(int cardId, String cardNumberHash, String cardholderName, LocalDate expiryDate) {
        this.cardId = cardId;
        this.cardNumberHash = cardNumberHash;
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumberHash() {
        return cardNumberHash;
    }

    public void setCardNumberHash(String cardNumberHash) {
        this.cardNumberHash = cardNumberHash;
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
