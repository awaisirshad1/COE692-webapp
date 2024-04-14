# Use the official OpenJDK base image
FROM openjdk:21-slim

# Args

ARG DB_URL=$DB_URL
ARG EXTERNALENDPOINTURI=$EXTERNALENDPOINTURI
ARG ALLOWED_CORS=$ALLOWED_CORS
# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built by maven into the container
COPY target/*.jar app.jar


# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]