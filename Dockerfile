# Use the official Maven image to build the application
FROM maven:3.9.9-amazoncorretto-17-debian AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="nodeninja"

WORKDIR /app

# Copy the jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]