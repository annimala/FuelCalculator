package com.example.fuelcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculationRecordTest {

    @Test
    void testConstructorAndGetters() {
        CalculationRecord calcRec = new CalculationRecord(
                150.0, 7.5, 1.85, 11.25, 20.81, "EN"
        );

        assertEquals(150.0, calcRec.getDistance(), 0.001);
        assertEquals(7.5, calcRec.getConsumption(), 0.001);
        assertEquals(1.85, calcRec.getPrice(), 0.001);
        assertEquals(11.25, calcRec.getTotalFuel(), 0.001);
        assertEquals(20.81, calcRec.getTotalCost(), 0.001);
        assertEquals("EN", calcRec.getLanguage());
    }

    @Test
    void testZeroValues() {
        CalculationRecord calcRec = new CalculationRecord(0, 0, 0, 0, 0, "FR");

        assertEquals(0.0, calcRec.getDistance());
        assertEquals(0.0, calcRec.getConsumption());
        assertEquals(0.0, calcRec.getPrice());
        assertEquals(0.0, calcRec.getTotalFuel());
        assertEquals(0.0, calcRec.getTotalCost());
        assertEquals("FR", calcRec.getLanguage());
    }

    @Test
    void testNegativeValues() {
        CalculationRecord calcRec = new CalculationRecord(-100, -5, -1.5, -5.0, -7.5, "JP");

        assertEquals(-100.0, calcRec.getDistance(), 0.001);
        assertEquals(-5.0, calcRec.getConsumption(), 0.001);
        assertEquals(-1.5, calcRec.getPrice(), 0.001);
        assertEquals(-5.0, calcRec.getTotalFuel(), 0.001);
        assertEquals(-7.5, calcRec.getTotalCost(), 0.001);
        assertEquals("JP", calcRec.getLanguage());
    }

    @Test
    void testLargeValues() {
        CalculationRecord calcRec = new CalculationRecord(
                100000.0, 15.0, 2.5, 15000.0, 37500.0, "IR"
        );

        assertEquals(100000.0, calcRec.getDistance(), 0.001);
        assertEquals(15.0, calcRec.getConsumption(), 0.001);
        assertEquals(2.5, calcRec.getPrice(), 0.001);
        assertEquals(15000.0, calcRec.getTotalFuel(), 0.001);
        assertEquals(37500.0, calcRec.getTotalCost(), 0.001);
        assertEquals("IR", calcRec.getLanguage());
    }

    @Test
    void testAllSupportedLanguages() {
        String[] langs = {"EN", "FR", "JP", "IR"};
        for (String lang : langs) {
            CalculationRecord calcRec = new CalculationRecord(1, 1, 1, 1, 1, lang);
            assertEquals(lang, calcRec.getLanguage());
        }
    }
}