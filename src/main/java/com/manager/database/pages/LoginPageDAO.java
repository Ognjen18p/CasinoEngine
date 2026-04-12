package com.manager.database.pages;

import com.basis.person.Person;
import com.basis.person.Player;
import com.manager.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class LoginPageDAO {
    private final DatabaseManager databaseManager;

    public LoginPageDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Player findPlayer(String username, String password) {
        String query = "SELECT * FROM player WHERE username = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String password_hash = resultSet.getString("password_hash");
                if(BCrypt.checkpw(password, password_hash)){
                    Player player = new Player();
                    player.setId(resultSet.getInt("person_id"));
                    player.setFirstName(resultSet.getString("first_name"));
                    player.setLastName(resultSet.getString("last_name"));
                    player.setEmail(resultSet.getString("email"));
                    player.setUsername(resultSet.getString("username"));
                    player.setPassword(resultSet.getString("password_hash"));
                    player.setBalance(resultSet.getDouble("balance"));
                    player.setRole(Person.Role.valueOf(resultSet.getString("role")));
                    player.setDateCreated(resultSet.getDate("date_created"));
                    return player;
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

}
