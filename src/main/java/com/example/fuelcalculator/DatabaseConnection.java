package com.example.fuelcalculator;

import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;



public class DatabaseConnection {
    private DatabaseConnection() {
        /* This utility class should not be instantiated */
    }

    private static final Properties properties = new Properties();

    FileInputStream fis;

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    private static final String URL = properties.getProperty("db.url");
    private static final String USER = properties.getProperty("db.user");
    private static final String PASSWORD = properties.getProperty("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
