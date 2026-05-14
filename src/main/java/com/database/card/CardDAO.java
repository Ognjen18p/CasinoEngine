package com.database.card;

import com.basis.card.Card;
import com.database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    public List<Card> getSavedCard(int personId)
    {
        String query = "SELECT * FROM card WHERE person_id = ? ORDER BY expiry_date DESC";
        try (PreparedStatement statement = DatabaseManager.getInstance().getConnection().prepareStatement(query)){
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();
            List<Card> userSavedCards = new ArrayList<>();
            while (resultSet.next()){
                userSavedCards.add(new Card(resultSet.getInt("card_id"),
                                                    resultSet.getString("card_number"),
                                                    resultSet.getString("cardholder_name"),
                                                    resultSet.getDate("expiry_date").toLocalDate()));
            }
            return userSavedCards;
        } catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    public void saveCard(int personId, Card debitCard){
        String query = "INSERT INTO card ( card_number,cardholder_name,expiry_date) VALUES (?,?,?)";
    }
}
