# build stage
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# final stage
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
