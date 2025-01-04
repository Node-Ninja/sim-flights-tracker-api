FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="nodeninja"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]