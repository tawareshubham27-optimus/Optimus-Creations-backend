# ------------------------------------
# Stage 1: Build the JAR (The Builder Stage)
# ------------------------------------
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copy the necessary files for the Maven build
COPY pom.xml .
COPY src ./src

# Build the application inside the container
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# ------------------------------------
# Stage 2: Create the Final Runtime Image
# ------------------------------------
# 
# FIX: Changed 'openjdk:17-jre-slim' to the more specific 'eclipse-temurin:17-jre-alpine'
# The Eclipse Temurin images are very reliable and 'alpine' is a tiny, secure base.
#
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/optimus-0.0.1-SNAPSHOT.jar app.jar

# Define the port and startup command
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
