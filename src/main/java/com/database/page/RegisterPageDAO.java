package com.database.page;

import com.basis.person.Player;
import com.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class RegisterPageDAO {
    private String errorMessage = "";

    public RegisterPageDAO(){}

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean emailExists(String email) {
        String query = "SELECT * FROM person WHERE email = ?";
        try (PreparedStatement preparedStatement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                errorMessage = "Email already exists";
                return true;
            }
            return false;
        } catch (SQLException exception) {
            errorMessage = "Email exist" + exception.getMessage();
            return false;
        }
    }

    public boolean usernameExists(String username) {
        String query = "SELECT * FROM person WHERE username = ?";
        try (PreparedStatement preparedStatement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                errorMessage = "Username already exists";
                return true;
            }
            return false;
        } catch (SQLException exception) {
            errorMessage = "Username doesn't exist" + exception.getMessage();
            return false;
        }
    }

    public boolean createPlayer(Player player) {
        String personQuery = "INSERT INTO person (first_name, last_name, email, username, password_hash, role, date_created) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String playerQuery = "INSERT INTO player (person_id, balance) VALUES (?, ?)";
        try (PreparedStatement personStatement = DatabaseManager.getInstance().getConnection().prepareStatement(personQuery, Statement.RETURN_GENERATED_KEYS)) {
            personStatement.setString(1, player.getFirstName());
            personStatement.setString(2, player.getLastName());
            personStatement.setString(3, player.getEmail());
            personStatement.setString(4, player.getUsername());
            personStatement.setString(5, BCrypt.hashpw(player.getPassword(), BCrypt.gensalt()));
            personStatement.setString(6, player.getRole().toString());
            personStatement.setDate(7, new Date(player.getDateCreated().getTime()));
            personStatement.executeUpdate();

            ResultSet keys = personStatement.getGeneratedKeys();
            if (!keys.next()) {
                errorMessage = "Couldn't create person";
                return false;
            }
            int personId = keys.getInt(1);

            PreparedStatement playerStatement = DatabaseManager.getInstance().getConnection().prepareStatement(playerQuery);
            playerStatement.setInt(1, personId);
            playerStatement.setDouble(2, player.getBalance());
            if(playerStatement.executeUpdate() <= 0){
                errorMessage = "Person could not be created";
                return false;
            }
            return true;
        } catch (SQLException exception) {
            errorMessage = exception.getMessage();
            return false;
        }
    }
}
