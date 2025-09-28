# ------------------------------------
# Stage 1: Build the JAR (The Builder Stage)
# ------------------------------------
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# 1. Copy the necessary files for the Maven build
# Copying pom.xml first allows Docker to cache the dependencies layer
COPY pom.xml .
COPY src ./src

# 2. Build the application inside the container
# Use the cache mount for Maven dependencies for faster subsequent builds
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# ------------------------------------
# Stage 2: Create the Final Runtime Image
# ------------------------------------
# Use a lighter JRE image for the final production container
FROM openjdk:17-jre-slim
WORKDIR /app

# 3. Copy the built JAR from the builder stage
# This is the crucial line that solves the "no such file or directory" error
COPY --from=builder /app/target/optimus-0.0.1-SNAPSHOT.jar app.jar

# 4. Define the port and startup command
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
