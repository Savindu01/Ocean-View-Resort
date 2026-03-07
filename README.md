# Ocean View Resort - Spring Boot Edition

A modern hotel reservation system built with **Spring Boot 3.2**, **Spring Data JPA**, and **React 18**.

### Key Stacks

| Feature | Pure Java | Spring Boot |
|---------|-----------|-------------|
| **HTTP Server** | Manual `HttpServer` | Embedded Tomcat |
| **JSON Handling** | Manual parsing | Jackson (automatic) |
| **Database** | JDBC + SQL | Spring Data JPA (ORM) |
| **Dependency Injection** | Manual `new` | @Autowired |
| **Configuration** | Hardcoded | application.properties |
| **Routing** | Manual handlers | @RestController annotations |
| **Validation** | Manual checks | @Valid annotations |
| **Testing** | JUnit + Mockito | Spring Boot Test |

---

## 📦 Technology Stack

### Backend
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Database abstraction (Hibernate)
- **Spring Web** - REST API (embedded Tomcat)
- **Spring Validation** - Input validation
- **MySQL 8.0** - Database
- **Java 17** - Programming language

### Frontend
- **React 18** - UI framework
- **Vite** - Build tool
- **Material-UI** - Component library

---

## 🏗️ Project Structure

```
eresortjava-springboot/
├── src/main/java/com/oceanview/eresort/
│   ├── OceanViewResortApplication.java    # Main Spring Boot app
│   ├── model/                              # JPA Entities
│   │   ├── User.java                       # @Entity with @Id
│   │   ├── Reservation.java                # @Entity with validations
│   │   └── RoomType.java                   # Enum
│   ├── repository/                         # Spring Data JPA
│   │   ├── UserRepository.java             # extends JpaRepository
│   │   └── ReservationRepository.java      # Auto-generated queries
│   ├── service/                            # Business Logic
│   │   ├── AuthService.java                # @Service
│   │   └── ReservationService.java         # @Transactional
│   ├── controller/                         # REST Controllers
│   │   ├── AuthController.java             # @RestController
│   │   └── ReservationController.java      # @RequestMapping
│   ├── dto/                                # Data Transfer Objects
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── ReservationRequest.java
│   │   └── ReservationResponse.java
│   └── config/                             # Configuration
│       ├── CorsConfig.java                 # CORS setup
│       └── DataInitializer.java            # Startup initialization
├── src/main/resources/
│   └── application.properties              # Spring Boot config
└── pom.xml                                 # Maven dependencies
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Node.js 16+ (for frontend)

### 1. Setup Database

```bash
mysql -u root -p
CREATE DATABASE ocean_view_resort;
```

### 2. Configure Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.password=your_password
```

Or set environment variable:
```bash
export DB_PASS=your_password
```

### 3. Run Backend

```bash
cd eresortjava-springboot

# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/eresort-springboot-1.0.0.jar
```

Backend will start on: **http://localhost:8080**

### 4. Run Frontend

```bash
cd ../eresortjava/frontend
npm install
npm run dev
```

Frontend will start on: **http://localhost:5173**

### 5. Access Application

Open browser: **http://localhost:5173**

**Default Login:**
- Username: `admin`
- Password: `admin123`

---

## 📚 API Endpoints

### Authentication
```
POST   /api/login              # Login with credentials
GET    /api/users/exists       # Check if username exists
```

### Reservations
```
GET    /api/reservations                    # Get all reservations
GET    /api/reservations?guestName=John     # Search by guest name
GET    /api/reservations?roomType=SUITE     # Search by room type
GET    /api/reservations?fromDate=2026-03-01&toDate=2026-03-31  # Date range
GET    /api/reservations/{id}               # Get specific reservation
POST   /api/reservations                    # Create reservation
PUT    /api/reservations/{id}               # Update reservation
DELETE /api/reservations/{id}               # Delete reservation
```

### Example Request

**Create Reservation:**
```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "guestName": "John Silva",
    "address": "Galle",
    "contactNumber": "0771234567",
    "roomType": "SUITE",
    "checkInDate": "2026-03-06",
    "checkOutDate": "2026-03-09"
  }'
```

---

## 🔑 Key Spring Boot Features Used

### 1. **Dependency Injection**

```java
@Service
public class ReservationService {
    
    @Autowired  // Spring automatically injects this!
    private ReservationRepository repository;
    
    // No need for: new ReservationRepository()
}
```

### 2. **Spring Data JPA - Auto-Generated Queries**

```java
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    // Spring generates SQL automatically!
    List<Reservation> findByGuestNameContainingIgnoreCase(String name);
    List<Reservation> findByRoomType(RoomType type);
}
```

### 3. **Automatic JSON Conversion**

```java
@RestController
public class ReservationController {
    
    @GetMapping("/api/reservations")
    public List<Reservation> getAll() {
        return service.getAllReservations();
        // Spring automatically converts to JSON!
    }
}
```

### 4. **Validation**

```java
@Entity
public class Reservation {
    
    @NotBlank(message = "Guest name is required")
    private String guestName;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Must be 10 digits")
    private String contactNumber;
}
```

### 5. **JPA Entity Mapping**

```java
@Entity
@Table(name = "reservations")
public class Reservation {
    
    @Id
    private String reservationNumber;
    
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

---

## 🧪 Testing

### Run Tests

```bash
mvn test
```

### Test Structure

```
src/test/java/com/oceanview/eresort/
├── service/
│   ├── ReservationServiceTest.java    # Unit tests with @Mock
│   └── AuthServiceTest.java
└── controller/
    └── ReservationControllerTest.java  # Integration tests
```

---

## 🔧 Configuration

### application.properties

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ocean_view_resort
spring.datasource.username=root
spring.datasource.password=${DB_PASS:root}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update  # Auto-create/update tables
spring.jpa.show-sql=true              # Show SQL in console

# Logging
logging.level.com.oceanview=DEBUG
```

---

## 📊 Comparison: Before vs After

### Creating a Reservation

**Before (Pure Java):**
```java
// Manual JSON parsing
String body = readBody(exchange.getRequestBody());
String guestName = extractJsonField(body, "guestName");

// Manual SQL
String sql = "INSERT INTO reservations VALUES (?, ?, ?, ?, ?, ?, ?)";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, reservationNumber);
// ... set all parameters

// Manual JSON response
String json = "{\"reservationNumber\":\"" + r.getReservationNumber() + "\"}";
```

**After (Spring Boot):**
```java
@PostMapping("/api/reservations")
public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
    Reservation r = service.createReservation(...);
    return ResponseEntity.ok(r);  // That's it!
}
```

---

## 🚢 Deployment

### Build JAR

```bash
mvn clean package
```

### Run JAR

```bash
java -jar target/eresort-springboot-1.0.0.jar
```

### Docker (Optional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/eresort-springboot-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 🎯 Benefits of Spring Boot Version

✅ **Less Code** - 50% less boilerplate  
✅ **Auto-Configuration** - Database, JSON, CORS all automatic  
✅ **Production Ready** - Health checks, metrics, monitoring built-in  
✅ **Industry Standard** - Used by most Java enterprises  
✅ **Better Testing** - Spring Boot Test framework  
✅ **Faster Development** - Hot reload with DevTools  
✅ **Easier Maintenance** - Clear structure and conventions  

---

## 📖 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building REST APIs](https://spring.io/guides/tutorials/rest/)

---

## 🤝 Contributing

This is an educational project demonstrating Spring Boot refactoring.

---

## 📄 License

Educational project for learning Spring Boot.

---

**Version:** 1.0.0 (Spring Boot Edition)  
**Last Updated:** March 6, 2026  
**Original:** Pure Java with HttpServer  
**Refactored:** Spring Boot 3.2 with Spring Data JPA
