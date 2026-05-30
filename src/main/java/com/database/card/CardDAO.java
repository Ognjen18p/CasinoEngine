package com.database.card;

import com.basis.card.Card;
import com.database.DatabaseManager;

import java.sql.*;

public class CardDAO {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean saveCard(int personId, Card card){
        String query = "INSERT INTO card (person_id, card_number, cardholder_name,expiry_date) VALUES (?, ?,?,?)";
        try(PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, personId);
            statement.setString(2, card.getCardNumberHash());
            statement.setString(3, card.getCardholderName());
            statement.setDate(4, java.sql.Date.valueOf(card.getExpiryDate()));
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()) card.setCardId(keys.getInt(1));
            return true;
        } catch (Exception e) {
            errorMessage = "Failed to save card! " + e.getMessage();
            return false;
        }
    }

    public boolean cardExists(int personId, Card card) {
        String query = "SELECT card_id FROM card WHERE person_id = ? AND card_number = ?";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, personId);
            statement.setString(2, card.getCardNumberHash());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            errorMessage = "Card check failed: " + e.getMessage();
            return false;
        }
    }

    public int getCardId(int personId, Card card) {
        String query = "SELECT card_id FROM card WHERE person_id = ? AND card_number = ?";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, personId);
            statement.setString(2, card.getCardNumberHash());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("card_id");
        } catch (Exception e) {
            errorMessage = "Could not get card id: " + e.getMessage();
        }
        return -1;
    }

}
