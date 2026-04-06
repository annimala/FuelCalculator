-- create database
CREATE DATABASE IF NOT EXISTS fuel_calculator_localization
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE fuel_calculator_localization;


-- create user
CREATE USER IF NOT EXISTS 'calc_user'@'localhost' IDENTIFIED BY 'pasu';

GRANT ALL PRIVILEGES ON fuel_calculator_localization.* TO 'calc_user'@'localhost';

FLUSH PRIVILEGES;


-- create tables
CREATE TABLE IF NOT EXISTS calculation_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    distance DOUBLE NOT NULL,
    consumption DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    total_fuel DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL,
    UNIQUE KEY unique_key_lang (`key`, `language`)
    );

--insert data
INSERT INTO localization_strings (`key`, value, language) VALUES

('distance.label', 'Distance (km):', 'EN'),
('consumption.label', 'Consumption (L/100km):', 'EN'),
('price.label', 'Fuel Price (€/L):', 'EN'),
('result.label', 'Fuel needed: {0} L, Total cost: {1} €', 'EN'),
('invalid.input', 'Invalid input! Please enter positive numbers.', 'EN'),

('distance.label', 'Distance (km) :', 'FR'),
('consumption.label', 'Consommation (L/100km) :', 'FR'),
('price.label', 'Prix du carburant (€/L) :', 'FR'),
('result.label', 'Carburant nécessaire : {0} L, Coût total : {1} €', 'FR'),
('invalid.input', 'Entrée invalide ! Veuillez entrer des nombres positifs.', 'FR'),

('distance.label', '距離 (km):', 'JP'),
('consumption.label', '燃費 (L/100km):', 'JP'),
('price.label', '燃料価格 (€/L):', 'JP'),
('result.label', '必要な燃料: {0} L、合計コスト: {1} €', 'JP'),
('invalid.input', '無効な入力です！正の数を入力してください。', 'JP'),

('distance.label', 'مسافت (کیلومتر):', 'IR'),
('consumption.label', 'مصرف (لیتر/100km):', 'IR'),
('price.label', 'قیمت سوخت (€/لیتر):', 'IR'),
('result.label', 'سوخت مورد نیاز: {0} لیتر، هزینه کل: {1} €', 'IR'),
('invalid.input', 'ورودی نامعتبر! لطفاً اعداد مثبت وارد کنید.', 'IR');
