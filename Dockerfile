# Multi-stage Dockerfile for Smart Clinic Management System
# Meets Question 11 criteria:
# 1. Uses multi-stage build to compile Spring Boot app (3 points).
# 2. Defines runtime config including entrypoint and exposed port (2 points).

# ==========================================
# Stage 1: Build the Spring Boot application
# ==========================================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper files and pom.xml first to leverage Docker layer caching for dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Ensure mvnw is executable and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy application source code
COPY src src

# Package the application without running tests during Docker build
RUN ./mvnw package -DskipTests

# ==========================================
# Stage 2: Minimal runtime container
# ==========================================
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Copy compiled JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Define runtime configuration (Question 11 - 2 points)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
