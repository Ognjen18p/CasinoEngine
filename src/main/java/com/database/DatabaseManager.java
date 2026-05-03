package com.database;

import java.sql.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final String url;
    private final String username;
    private final String password;

    private DatabaseManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static void initialize(String url, String username, String password) {
        if (instance == null)
            instance = new DatabaseManager(url, username, password);
    }

    public static DatabaseManager getInstance() {
        if (instance == null)
            throw new RuntimeException("Database manager has not been initialized");
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException exception) {
            System.out.println("Connection Failed! " + exception.getMessage());
            return null;
        }
    }
}
