package com.application.configuration;

public class GameSettings {
    private static GameSettings instance;

    /// Display
    private double windowWidth = 1000;
    private double windowHeight = 800;
    private double volume = 0.8;
    private boolean soundEnabled = true;

    /// Payment
    public enum Currency {
        USD("usd",100), EUR("eur", 100), GBP("gbp",100);

        private final String stripeCode;
        private final int centValue;

        Currency(String stripeCode, int centValue) {
            this.stripeCode = stripeCode;
            this.centValue = centValue;
        }

        public String getStripeCode() {
            return stripeCode;
        }

        public long toCentValue(double amount) {
            return (long) (centValue * amount);
        }
    }

    private Currency currency = Currency.USD;

    public GameSettings() {
    }

    public static GameSettings getInstance() {
        if (instance == null) instance = new GameSettings();
        return instance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public static void setInstance(GameSettings instance) {
        GameSettings.instance = instance;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
