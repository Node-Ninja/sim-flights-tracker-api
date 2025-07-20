# Use a lightweight OpenJDK base image
FROM eclipse-temurin:17-jdk-alpine

LABEL author="NodeNinja"
LABEL version="2.8.1"
LABEL description="Sim Flights Tracker API"

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
# Replace 'target/myapp.jar' with your actual jar file path
COPY target/simflightstracker-2.8.1.jar sft.jar

# Expose the port your Spring Boot app listens on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "sft.jar"]