package com.database.transaction;

import com.basis.transaction.Transaction;
import com.database.DatabaseManager;

import java.util.Date;
import java.sql.*;

public class TransactionDAO {
    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean createTransaction(Transaction transaction) {
        String query = "INSERT INTO transaction (card_id, person_id, amount, status, type, time_created) " + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaction.getCardId());
            statement.setInt(2, transaction.getPersonId());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getStatus().toString());
            statement.setString(5, transaction.getType().toString());
            statement.setDate(6, new java.sql.Date(new Date().getTime()));
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) transaction.setTransactionID(keys.getInt(1));
            return true;
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
            return false;
        }
    }

    public boolean updateStatus(int transactionId, Transaction.Status status) {
        String query = "UPDATE transaction SET status = ?, time_finished = ? " + "WHERE transaction_id = ?";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, status.toString());
            statement.setDate(2, new java.sql.Date(new Date().getTime()));
            statement.setInt(3, transactionId);
            return statement.executeUpdate() > 0;
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
            return false;
        }
    }
}