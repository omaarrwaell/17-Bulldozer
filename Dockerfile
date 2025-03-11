# Use OpenJDK base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/mini1.jar app.jar

# Create a directory for storing JSON data inside the container
RUN mkdir -p /app/data

# Copy the default JSON data files into the container
COPY src/main/java/com/example/data/ /app/data/


# Set environment variables to update JSON file paths inside the container
ENV USER_DATA_PATH="/app/data/users.json"
ENV PRODUCT_DATA_PATH="/app/data/products.json"
ENV CART_DATA_PATH="/app/data/carts.json"
ENV ORDER_DATA_PATH="/app/data/orders.json"

# Expose the application's running port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
