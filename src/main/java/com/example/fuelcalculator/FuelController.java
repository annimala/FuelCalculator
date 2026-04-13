package com.example.fuelcalculator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FuelController implements Initializable {

    @FXML private Label lblDistance;
    @FXML private Label lblConsumption;
    @FXML private Label lblPrice;
    @FXML private Label lblResult;

    @FXML private TextField txtDistance;
    @FXML private TextField txtConsumption;
    @FXML private TextField txtPrice;

    private final LocalizationService localizationService = new LocalizationService();
    private final CalculationService calculationService = new CalculationService();
    private String currentLang = "EN";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage("EN");
    }

    @FXML
    private void handleEN() {
        setLanguage("EN");
    }

    @FXML
    private void handleFR() {
        setLanguage("FR");
    }

    @FXML
    private void handleJP() {
        setLanguage("JP");
    }

    @FXML
    private void handleIR() {
        setLanguage("IR");
    }

    @FXML
    private javafx.scene.layout.VBox root;


    private void setLanguage(String lang) {
        currentLang = lang;
        localizationService.loadStrings(lang);
        lblDistance.setText(localizationService.getString("distance.label"));
        lblConsumption.setText(localizationService.getString("consumption.label"));
        lblPrice.setText(localizationService.getString("price.label"));
        lblResult.setText("");

        if (lang.equals("IR")) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }

    @FXML
    private void handleCalculate() {
        try {
            double distance = Double.parseDouble(txtDistance.getText().trim());
            double consumption = Double.parseDouble(txtConsumption.getText().trim());
            double price = Double.parseDouble(txtPrice.getText().trim());

            double totalFuel = (consumption / 100.0) * distance;
            double totalCost = totalFuel * price;

            String resultTemplate = localizationService.getString("result.label");
            String result = MessageFormat.format(
                    resultTemplate,
                    String.format("%.2f", totalFuel),
                    String.format("%.2f", totalCost)
            );
            lblResult.setText(result);

            CalculationRecord record = new CalculationRecord(distance, consumption, price, totalFuel, totalCost, currentLang);
            calculationService.saveCalculation(record);

        } catch (NumberFormatException e) {
            lblResult.setText(localizationService.getString("invalid.input"));
        }
        catch (Exception e) {
            //lblResult.setText(bundle.getString("invalid.input"));
            lblResult.setText("Error: " + e.getMessage());
        }
    }
}