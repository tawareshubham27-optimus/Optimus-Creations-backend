# ------------------------------------
# Stage 1: Build the JAR (The Builder Stage)
# FIX: Use the stable Maven 3 with OpenJDK 17 tag
# ------------------------------------
FROM maven:3-openjdk-17 AS builder 
WORKDIR /app

# 1. Copy the necessary files for the Maven build
COPY pom.xml .
COPY src ./src

# 2. Build the application inside the container
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# ------------------------------------
# Stage 2: Create the Final Runtime Image
# ------------------------------------
# Use the minimal JRE image for the final production container
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 3. Copy the built JAR from the builder stage
COPY --from=builder /app/target/optimus-0.0.1-SNAPSHOT.jar app.jar

# 4. Define the port and startup command
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
