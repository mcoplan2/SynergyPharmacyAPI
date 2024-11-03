# Use a Gradle base image
FROM gradle:jdk11 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY --chown=gradle:gradle build.gradle settings.gradle /app/
COPY --chown=gradle:gradle src /app/src

# Build the application
RUN gradle build

# Use a smaller base image for the runtime
FROM openjdk:11-jre-slim

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]