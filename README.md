# Ocean View Resort Management System

A modern hotel reservation system built with **Spring Boot 3.2** and **React 18**.

## 📁 Project Structure

```
.
├── eresortjava-springboot/          # Spring Boot Backend
│   ├── src/main/java/               # Java source code
│   ├── src/main/resources/          # Configuration files
│   ├── pom.xml                      # Maven dependencies
│   └── mvnw.cmd                     # Maven wrapper (no installation needed)
│
├── eresortjava/
│   ├── frontend/                    # React Frontend
│   │   ├── src/                     # React components
│   │   ├── package.json             # NPM dependencies
│   │   └── index.html               # Entry point
│   │
│   ├── sql/                         # Database
│   │   └── schema.sql               # MySQL schema
│   │
│   ├── Report/                      # Documentation
│   │   ├── Technical_Report.md      # Part 1
│   │   └── Technical_Report_Part2.md # Part 2
│   │
│   └── report images/               # UML diagrams
│
└── README.md                        # This file
```

---

## 🚀 Quick Start

### 1. Setup Database

```bash
mysql -u root -p
CREATE DATABASE ocean_view_resort;
USE ocean_view_resort;
SOURCE eresortjava/sql/schema.sql;
```

### 2. Run Backend (Spring Boot)

```bash
cd eresortjava-springboot

# Set database password
$env:DB_PASS = "your_password"

# Run using Maven Wrapper (no Maven installation needed!)
.\mvnw.cmd spring-boot:run
```

Backend runs on: **http://localhost:8080**

### 3. Run Frontend (React)

```bash
cd eresortjava/frontend
npm install
npm run dev
```

Frontend runs on: **http://localhost:5173**

### 4. Login

- Username: `admin`
- Password: `admin123`

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Backend Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL 8.0 |
| Language | Java 17 |
| Frontend | React 18 + Vite |
| Build Tool | Maven 3.9 |

---

## 📚 Documentation

- **[Backend README](eresortjava-springboot/README.md)** - Complete Spring Boot guide
- **[Technical Report Part 1](eresortjava/Report/Technical_Report.md)** - System design & UML
- **[Technical Report Part 2](eresortjava/Report/Technical_Report_Part2.md)** - Implementation & testing
- **[Migration Guide](eresortjava-springboot/MIGRATION_GUIDE.md)** - Pure Java → Spring Boot
- **[Project Structure](eresortjava-springboot/PROJECT_STRUCTURE.md)** - Architecture details

---

## 💰 Room Pricing

| Room Type | Price/Night |
|-----------|-------------|
| Single    | LKR 5,000   |
| Double    | LKR 8,000   |
| Family    | LKR 12,000  |
| Suite     | LKR 20,000  |

---

## 🎯 Features

- User authentication with secure password hashing
- Complete reservation CRUD operations
- Advanced search (by guest name, room type, dates)
- Bill generation with print support
- Dashboard with booking statistics
- Responsive design (desktop, tablet, mobile)

---

## 📝 Prerequisites

- **Java 17+** - [Download](https://adoptium.net/)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/)
- **Node.js 16+** - [Download](https://nodejs.org/)
- **Maven** - Not required! Use included Maven Wrapper (`mvnw.cmd`)

---

## 🐛 Troubleshooting

**Backend won't start:**
- Check MySQL is running
- Verify database exists
- Set DB_PASS environment variable

**Frontend won't start:**
- Run `npm install` in frontend directory
- Check Node.js version: `node -v`

**Port conflicts:**
- Backend: 8080
- Frontend: 5173

See detailed troubleshooting in [Backend README](eresortjava-springboot/README.md).

---

## 📄 License

Educational project for learning Spring Boot and hotel management systems.

---

**Version:** 1.0.0  
**Last Updated:** March 2026  
**Framework:** Spring Boot 3.2 with Spring Data JPA


Backend
.\mvnw.cmd spring-boot:run

Frontend
npm run dev
