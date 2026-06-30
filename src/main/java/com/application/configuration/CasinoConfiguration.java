package com.application.configuration;

public class CasinoConfiguration {
    public static final double MIN_DEPOSIT = 100.0;
    public static final double MAX_DEPOSIT = 100_000.0;
    public static final double MIN_WITHDRAW = 1_000.0;
    public static final double MAX_WITHDRAW = 100_000.0;
    public static final int MIN_BET = 10;
    public static final int MAX_BET = 10_000;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int  MIN_LASTNAME_LENGTH = 2;
    public static final int MAX_LASTNAME_LENGTH = 50;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 30;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final long LOCK_DURATION = 5 * 60 * 100;
    public static final long SESSION_DURATION = 24  * 60 * 1000;
    public static final long TOKEN_EXPIRE_DURATION = 5 * 60 * 1000;
    public static final int[] BETTING_VALUES = new int[] { 10, 20, 50, 100, 200, 500 };
}
