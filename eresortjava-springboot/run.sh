#!/bin/bash

# Ocean View Resort - Spring Boot Quick Start Script

echo "🏨 Ocean View Resort - Spring Boot Edition"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if MySQL is running
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL client not found. Make sure MySQL server is running."
fi

echo "✅ Maven found"
echo ""

# Set database password
if [ -z "$DB_PASS" ]; then
    echo "⚠️  DB_PASS environment variable not set. Using default 'root'"
    export DB_PASS=root
fi

echo "🔧 Building Spring Boot application..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting Spring Boot application..."
    echo "   Backend will run on: http://localhost:8080"
    echo "   Default login: admin / admin123"
    echo ""
    echo "Press Ctrl+C to stop the server"
    echo ""
    
    java -jar target/eresort-springboot-1.0.0.jar
else
    echo ""
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
