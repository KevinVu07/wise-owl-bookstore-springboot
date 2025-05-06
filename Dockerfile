# Use an official OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Download dependencies (leverages Docker cache)
RUN ./mvnw dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Run the application
ENTRYPOINT ["java","-jar","target/bookstore-0.0.1-SNAPSHOT.jar"]
