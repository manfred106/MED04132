FROM docker.io/library/openjdk:17-ea-5
EXPOSE 8080

# Set the working directory in the container
WORKDIR /app
COPY build/libs/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]