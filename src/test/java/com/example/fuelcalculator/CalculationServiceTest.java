package com.example.fuelcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    private CalculationService calculationService;
    private CalculationRecord sampleRecord;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
        sampleRecord = new CalculationRecord(150.0, 7.5, 1.85, 11.25, 20.81, "EN");
    }

    @Test
    void testSaveCalculation_success() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            calculationService.saveCalculation(sampleRecord);

            verify(mockStmt).setDouble(1, 150.0);
            verify(mockStmt).setDouble(2, 7.5);
            verify(mockStmt).setDouble(3, 1.85);
            verify(mockStmt).setDouble(4, 11.25);
            verify(mockStmt).setDouble(5, 20.81);
            verify(mockStmt).setString(6, "EN");
            verify(mockStmt).executeUpdate();
        }
    }

    @Test
    void testSaveCalculation_sqlExceptionHandledGracefully() throws SQLException {
        Connection mockConn = mock(Connection.class);

        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            // Should not throw — exception is caught and printed internally
            assertDoesNotThrow(() -> calculationService.saveCalculation(sampleRecord));
        }
    }

    @Test
    void testSaveCalculation_connectionFailureHandledGracefully() {
        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection)
                    .thenThrow(new SQLException("Connection refused"));

            assertDoesNotThrow(() -> calculationService.saveCalculation(sampleRecord));
        }
    }

    @Test
    void testSaveCalculation_withFrenchLanguage() throws SQLException {
        CalculationRecord frRecord = new CalculationRecord(200.0, 8.0, 1.90, 16.0, 30.4, "FR");

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            calculationService.saveCalculation(frRecord);

            verify(mockStmt).setString(6, "FR");
        }
    }

    @Test
    void testSaveCalculation_withZeroValues() throws SQLException {
        CalculationRecord zeroRecord = new CalculationRecord(0, 0, 0, 0, 0, "JP");

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            assertDoesNotThrow(() -> calculationService.saveCalculation(zeroRecord));
        }
    }
}