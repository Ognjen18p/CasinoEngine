package com.database.person;

import com.application.GameManager;
import com.database.DatabaseManager;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAO {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean updateBalance(double amount) {
        double newBalance = GameManager.getInstance().getCurrentPlayer().getBalance() + amount;
        String query = "UPDATE player SET balance = ? WHERE person_id = ?";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, GameManager.getInstance().getCurrentPlayer().getId());
            if (statement.executeUpdate() > 0) {
                GameManager.getInstance().getCurrentPlayer().setBalance(newBalance);
                return true;
            }
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
        }
        return false;
    }

    public double getBalance() {
        String query = "SELECT balance FROM player WHERE person_id = ?";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, GameManager.getInstance().getCurrentPlayer().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getDouble("balance");
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
        }
        return -1;
    }

    public boolean purchaseAndSaleChip(int chipValue) {
        Connection connection = DatabaseManager.getInstance().getConnection();
        try {
            int playerId = GameManager.getInstance().getCurrentPlayer().getId();

            String balanceQuery = "UPDATE player SET balance = ? WHERE person_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(balanceQuery)) {
                stmt.setDouble(1, GameManager.getInstance().getCurrentPlayer().getBalance() + chipValue);
                stmt.setInt(2, playerId);
                stmt.executeUpdate();
            }

            String chipsQuery = "UPDATE player SET owning_chips = ? WHERE person_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(chipsQuery)) {
                stmt.setString(1, new Gson().toJson(GameManager.getInstance().getCurrentPlayer().getOwningChips()));
                stmt.setInt(2, playerId);
                stmt.executeUpdate();
            }
            return true;
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
            if (connection != null) try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return false;
        } finally {
            if (connection != null) try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean saleChip() {
        String chipsQuery = "UPDATE player SET owning_chips = ? WHERE person_id = ?";
        try (PreparedStatement stmt = DatabaseManager.getInstance().getConnection().prepareStatement(chipsQuery)) {
            stmt.setString(1, new Gson().toJson(GameManager.getInstance().getCurrentPlayer().getOwningChips()));
            stmt.setInt(2, GameManager.getInstance().getCurrentPlayer().getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
            return false;
        }
    }
}
