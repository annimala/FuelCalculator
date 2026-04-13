package com.example.fuelcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalizationServiceTest {

    private LocalizationService localizationService;

    @BeforeEach
    void setUp() {
        localizationService = new LocalizationService();
    }

    @Test
    void testGetString_missingKeyReturnsMissingPrefix() {
        // No strings loaded — should return fallback
        String result = localizationService.getString("some.key");
        assertEquals("MISSING: some.key", result);
    }

    @Test
    void testGetAllKeys_emptyInitially() {
        Map<String, String> keys = localizationService.getAllKeys();
        assertTrue(keys.isEmpty());
    }

    @Test
    void testLoadStrings_populatesMap() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);

        // Simulate two rows returned
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("key"))
                .thenReturn("distance.label", "price.label");
        when(mockRs.getString("value"))
                .thenReturn("Distance", "Price");

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            localizationService.loadStrings("EN");

            assertEquals("Distance", localizationService.getString("distance.label"));
            assertEquals("Price", localizationService.getString("price.label"));
        }
    }

    @Test
    void testLoadStrings_clearsOldStringsOnReload() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);

        // First load: return one key
        when(mockRs.next()).thenReturn(true, false, true, false);
        when(mockRs.getString("key")).thenReturn("distance.label", "prix.label");
        when(mockRs.getString("value")).thenReturn("Distance", "Prix");

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            localizationService.loadStrings("EN");
            assertEquals("Distance", localizationService.getString("distance.label"));

            localizationService.loadStrings("FR");
            // Old EN key should be gone
            assertEquals("MISSING: distance.label", localizationService.getString("distance.label"));
            assertEquals("Prix", localizationService.getString("prix.label"));
        }
    }

    @Test
    void testLoadStrings_sqlExceptionHandledGracefully() {
        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection)
                    .thenThrow(new SQLException("Connection failed"));

            assertDoesNotThrow(() -> localizationService.loadStrings("EN"));
            // Map remains empty after failure
            assertTrue(localizationService.getAllKeys().isEmpty());
        }
    }

    @Test
    void testGetString_returnsCorrectValueAfterLoad() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString("key")).thenReturn("result.label");
        when(mockRs.getString("value")).thenReturn("Total: {0}, Cost: {1}");

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            localizationService.loadStrings("EN");

            assertEquals("Total: {0}, Cost: {1}", localizationService.getString("result.label"));
        }
    }

    @Test
    void testGetAllKeys_returnsAllLoadedEntries() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, true, true, false);
        when(mockRs.getString("key"))
                .thenReturn("distance.label", "consumption.label", "price.label");
        when(mockRs.getString("value"))
                .thenReturn("Distance", "Consumption", "Price");

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            localizationService.loadStrings("EN");
            Map<String, String> all = localizationService.getAllKeys();

            assertEquals(3, all.size());
            assertTrue(all.containsKey("distance.label"));
            assertTrue(all.containsKey("consumption.label"));
            assertTrue(all.containsKey("price.label"));
        }
    }

    @Test
    void testLoadStrings_emptyResultSet() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            localizationService.loadStrings("XX");
            assertTrue(localizationService.getAllKeys().isEmpty());
        }
    }
}
