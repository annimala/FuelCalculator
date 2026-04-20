FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y \
        openjfx \
        libgtk-3-0 \
        libxtst6 \
        libxxf86vm1 \
        libgl1 \
        libgl1-mesa-dri \
        x11-utils \
        && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/FuelCalculator.jar app.jar

COPY config.properties config.properties

ENTRYPOINT ["java", \
                "-Dprism.order=sw", \
                "-Djava.library.path=/usr/lib/x86_64-linux-gnu/jni", \
                "--module-path", "/usr/share/openjfx/lib", \
                "--add-modules", "javafx.controls,javafx.fxml", \
                "-jar", "app.jar"]