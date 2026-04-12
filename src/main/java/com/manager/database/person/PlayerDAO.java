package com.manager.database.person;

import com.basis.person.Player;
import com.manager.database.DatabaseManager;

import java.sql.*;

public class PlayerDAO {
    private DatabaseManager databaseManager;

    public PlayerDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


}
