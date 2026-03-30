FROM openjdk:17-slim

RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libgtk-3-0 \
    openjfx \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/FuelCalculator.jar .

CMD ["java", "--module-path", "/usr/share/openjfx/lib", \
     "--add-modules", "javafx.controls,javafx.fxml", \
     "-jar", "FuelCalculator.jar"]