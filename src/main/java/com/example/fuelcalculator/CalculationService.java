package com.example.fuelcalculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalculationService {
    CalculationRecord record;

    public void saveCalculation(CalculationRecord record) {
        String sql = "INSERT INTO calculation_records " +
                "(distance, consumption, price, total_fuel, total_cost, language) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setDouble(1, record.getDistance());
            stmt.setDouble(2, record.getConsumption());
            stmt.setDouble(3, record.getPrice());
            stmt.setDouble(4, record.getTotalFuel());
            stmt.setDouble(5, record.getTotalCost());
            stmt.setString(6, record.getLanguage());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving calculation: " + e.getMessage());
        }

    }

}
