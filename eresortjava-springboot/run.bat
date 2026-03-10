@echo off
REM Ocean View Resort - Spring Boot Quick Start Script (Windows)

echo.
echo ========================================
echo   Ocean View Resort - Spring Boot
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven is not installed. Please install Maven first.
    exit /b 1
)

echo [OK] Maven found
echo.

REM Set database password if not set
if "%DB_PASS%"=="" (
    echo [WARNING] DB_PASS environment variable not set. Using default 'root'
    set DB_PASS=root
)

echo [INFO] Building Spring Boot application...
call mvn clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] Build successful!
    echo.
    echo [INFO] Starting Spring Boot application...
    echo    Backend will run on: http://localhost:8080
    echo    Default login: admin / admin123
    echo.
    echo Press Ctrl+C to stop the server
    echo.
    
    java -jar target\eresort-springboot-1.0.0.jar
) else (
    echo.
    echo [ERROR] Build failed. Please check the errors above.
    exit /b 1
)
