package com.database.page;

import com.basis.person.Person;
import com.basis.person.Player;
import com.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class LoginPageDAO {
    private String errorMessage = "";

    public LoginPageDAO() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Player findPlayer(String username, String password) {
        String query = "SELECT person.*, player.balance FROM person" +
                " JOIN player ON person.person_id = player.person_id" +
                " WHERE person.username = ?";
        try (PreparedStatement preparedStatement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password_hash = resultSet.getString("password_hash");
                if (BCrypt.checkpw(password, password_hash)) {
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
                errorMessage = "Wrong password";
                return null;
            }
            errorMessage = "Username doesn't exist";
            return null;
        } catch (SQLException exception) {
            errorMessage = "Username doesn't exist";
            return null;
        }
    }

}
