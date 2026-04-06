# Fuel Calculator Localization

This application is a JavaFX-based fuel cost calculator that supports multiple languages (EN, FR, JP, IR) and uses a MariaDB database for both localization and calculation storage.
Offers right-to-left support for Persian.


## Requirements

- Java 21
- Maven
- MariaDB


## Database Setup

1. Start MariaDB server

2. Open terminal and run:

mysql -u root -p < schema.sql

This will create database and required tables, insert localization strings and create user calc_user.

OR manually copy and paste sections (create user, create database, create tables, insert data) from schema.sql.

## Verify the setup

SELECT * FROM localization_strings;
SELECT * FROM calculation_records;

## Database configuration

To update database creditentials in DatabaseConnection.java
Example:
private static final String URL = "jdbc:mariadb://localhost:3306/fuel_calculator_localization";
private static final String USER = "example_name";
private static final String PASSWORD = "example_password";

## Run application

Run using maven:
mvn clean javafx:run
