#FROM openjdk:21-jdk
#VOLUME /tmp
#EXPOSE 8081
#ARG JAR_FILE=target/AccountsService-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} accountsService.jar

##WORKDIR /app
##COPY .mvn/ ./mvn
##COPY mvnw pom.xml ./
##RUN ./mvnw dependency:go-offline
##
##COPY src ./src
##CMD ["./mvnw", "spring-boot:run"]
#
#
# Use the official OpenJDK base image
FROM openjdk:21-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built by maven into the container
COPY target/*.jar app.jar

# Expose the port your application will run on
#EXPOSE 8081

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]