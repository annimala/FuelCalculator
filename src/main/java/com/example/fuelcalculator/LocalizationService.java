package com.example.fuelcalculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    private Map<String, String> strings = new HashMap<>();

    public void loadStrings(String language) {
        String sql =  "SELECT `key`, value FROM localization_strings WHERE language = ?";
        strings.clear();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, language);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                strings.put(rs.getString("key"), rs.getString("value"));
            }
            System.out.println("Loaded strings: " + strings);

        } catch (SQLException e) {
            System.out.println("Error loading localization: " + e.getMessage());
        }
    }

    public String getString(String key) {
        return strings.getOrDefault(key, "MISSING: " + key);
    }

    public Map<String, String> getAllKeys() {
        return strings;
    }
}
