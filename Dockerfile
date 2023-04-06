# Use official gradle image as base
FROM gradle:7.3.0-jdk11 AS build

# Copy source code and dependencies
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Run gradle build
RUN gradle build --no-daemon

# Use openjdk image for runtime
FROM openjdk:11-jre-slim

# Copy jar file from build stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Set entrypoint for console app
ENTRYPOINT ["java","-jar","/app/app.jar"]