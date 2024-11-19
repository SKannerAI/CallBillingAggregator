
# Stage 1: Build the application
FROM gradle:8.10.2-jdk17 AS build
LABEL authors="Sandeep kanparthy"

WORKDIR /app
# Copy only the Gradle configuration files first to cache dependencies
COPY build.gradle settings.gradle /app/

RUN ./gradlew clean build --stacktrace --no-daemon || return 0

COPY . /app

# Stage 2: Package the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/CallBillingAggregator.jar /app/CallBillingAggregator.jar

# Expose the application's port
EXPOSE 8080

# Set the entry point for the application
ENTRYPOINT ["java", "-jar", "/app/CallBillingAggregator.jar"]

