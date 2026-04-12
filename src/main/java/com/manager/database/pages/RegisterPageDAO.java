package com.manager.database.pages;

import com.basis.person.Player;
import com.manager.database.DatabaseManager;

import java.sql.*;

public class RegisterPageDAO {
    private final DatabaseManager databaseManager;

    public RegisterPageDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    private boolean playerExists(Player player) {
        String query = "SELECT * FROM player WHERE email = ? OR username = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getEmail());
            preparedStatement.setString(2, player.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery(query);
            return resultSet.next();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean createPlayer(Player player) {
        String query = "INSERT INTO player (first_name, last_name, email, username, password_hash, role, date_created, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getFirstName());
            preparedStatement.setString(2, player.getLastName());
            preparedStatement.setString(3, player.getEmail());
            preparedStatement.setString(4, player.getUsername());
            preparedStatement.setString(5, player.getPassword());
            preparedStatement.setString(6, player.getRole().toString());
            preparedStatement.setDate(7, new Date(player.getDateCreated().getTime()));
            preparedStatement.setDouble(8, player.getBalance());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
