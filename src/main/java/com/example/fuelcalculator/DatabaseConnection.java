package com.example.fuelcalculator;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/fuel_calculator_localization";
    private static final String USER = "calc_user";
    private static final String PASSWORD = "pasu";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
