# Dockerfile for backend

# Stage 1: Build
FROM maven:3.9.9-amazoncorretto-17 AS builder

# Set the working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM amazoncorretto:17-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
