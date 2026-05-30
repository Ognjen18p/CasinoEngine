package com.database.person;

import com.application.GameManager;
import com.database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            if(statement.executeUpdate() > 0) {
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
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)){
            statement.setInt(1, GameManager.getInstance().getCurrentPlayer().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getDouble("balance");
        } catch (Exception exception){
            errorMessage = exception.getMessage();
        }
        return -1;
    }

}
