package com.example.fuelcalculator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    private void handleEN() {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    private void handleFR() {
        setLanguage(new Locale("fr", "FR"));
    }

    @FXML
    private void handleJP() {
        setLanguage(new Locale("ja", "JP"));
    }

    @FXML
    private void handleIR() {
        setLanguage(new Locale("fa", "IR"));
    }


    private void setLanguage(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(
                    "com.example.fuelcalculator.messages", locale
            );
            lblDistance.setText(bundle.getString("distance.label"));
            lblConsumption.setText(bundle.getString("consumption.label"));
            lblPrice.setText(bundle.getString("price.label"));
            lblResult.setText("");
        } catch (MissingResourceException e) {
            lblResult.setText("Error: Language resource file not found.");
        }
    }

    @FXML
    private void handleCalculate() {
        try {
            double distance    = Double.parseDouble(txtDistance.getText().trim());
            double consumption = Double.parseDouble(txtConsumption.getText().trim());
            double price       = Double.parseDouble(txtPrice.getText().trim());

            double totalFuel = (consumption / 100.0) * distance;
            double totalCost = totalFuel * price;

            String resultTemplate = bundle.getString("result.label");
            String result = MessageFormat.format(
                    resultTemplate,
                    String.format("%.2f", totalFuel),
                    String.format("%.2f", totalCost)
            );
            lblResult.setText(result);

        } catch (NumberFormatException e) {
            lblResult.setText(bundle.getString("invalid.input"));
        }
    }
}