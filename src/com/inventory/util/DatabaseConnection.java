package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/inventory_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456"; 

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Veritabaný baðlantýsý baþarýyla saðlandý.");
            } catch (SQLException e) {
                System.err.println("[DB Hata] Baðlantý kurulamadý: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("[DB] Baðlantý kapatýldý.");
            } catch (SQLException e) {
                System.err.println("[DB Hata] Kapatma hatasý: " + e.getMessage());
            }
        }
    }
}