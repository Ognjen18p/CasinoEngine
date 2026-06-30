package com.database.session;

import com.application.configuration.CasinoConfiguration;
import com.basis.session.Session;
import com.database.DatabaseManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class SessionDAO {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean createSession(Session session) {
        String query = "INSERT INTO session (person_id, created_at, expire_at, token, state) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DatabaseManager.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, session.getPersonId());
            preparedStatement.setLong(2, session.getCreatedAt());
            preparedStatement.setLong(3, session.getExpiresAt());
            preparedStatement.setString(4, session.getToken());
            preparedStatement.setString(5, session.getState().toString());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) session.setSessionId(keys.getInt(1));
            return true;
        } catch (Exception exception) {
            errorMessage = "Failed to create Session " + exception.getMessage();
            return false;

        }
    }

    public String getOrCreateUUIDToken() {
        Path path = Paths.get(System.getProperty("user.home"), "uuid_token.txt");
        try {
            if (Files.exists(path)) {
                return Files.readString(path).trim();
            } else {
                String newId = UUID.randomUUID().toString();
                Files.createDirectories(path.getParent());
                Files.writeString(path, newId);
                return newId;
            }
        } catch (IOException e) {
            return UUID.randomUUID().toString();
        }
    }

    public Session checkSession() {
        String query = "SELECT session.* FROM session WHERE token = ?";
        try (PreparedStatement preparedStatement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, getOrCreateUUIDToken());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Session session = new Session();
                session.setSessionId(resultSet.getInt("session_id"));
                session.setPersonId(resultSet.getInt("person_id"));
                session.setExpiresAt(resultSet.getLong("expire_at"));
                session.setCreatedAt(resultSet.getLong("created_at"));
                session.setToken(resultSet.getString("token"));
                session.setState(Session.State.valueOf(resultSet.getString("state")));
                return session;
            }
            return null;
        } catch (SQLException exception) {
            errorMessage = "Session doesn't exist" + exception.getMessage();
            return null;
        }
    }
}
