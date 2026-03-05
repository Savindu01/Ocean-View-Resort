# Ocean View Resort - Full-Stack Application

A modern resort reservation system with a **React frontend** and **Java REST backend**.

## 🌟 Features

- **Modern React Frontend**: Black & blue theme with professional UI
- **Home Page**: Resort showcase with amenities and room types
- **User Authentication**: Secure login with SHA-256 hashed passwords
- **Reservation Management**: Complete CRUD operations
- **Dashboard**: Statistics cards showing booking analytics
- **Advanced Search**: Filter by guest name, room type, and dates
- **Bill Generation**: Professional invoice layout with print support
- **Responsive Design**: Works on desktop, tablet, and mobile

## 🚀 Quick Start

### Prerequisites

- Java 11+
- MySQL 5.7+
- Node.js 16+
- MySQL Connector/J 9.6.0

### Setup (5 minutes)

1. **Clone and setup:**
   ```bash
   git clone <repository-url>
   cd eresortjava
   ```

2. **Download MySQL Connector:**
   - Get from: https://dev.mysql.com/downloads/connector/j/
   - Place in: `lib/mysql-connector-j-9.6.0.jar`

3. **Setup database:**
   ```bash
   mysql -u root -p < sql/schema.sql
   ```

4. **Compile backend:**
   ```bash
   javac -encoding UTF-8 -d out -cp "lib/*" src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java src/*.java
   ```

5. **Install frontend:**
   ```bash
   cd frontend
   npm install
   cd ..
   ```

6. **Run (2 terminals):**
   
   Terminal 1 - Backend:
   ```bash
   $env:DB_PASS = "your_password"  # Windows
   java -cp "out;lib/*" RestServer
   ```
   
   Terminal 2 - Frontend:
   ```bash
   cd frontend
   npm run dev
   ```

7. **Access:** http://localhost:5174

**Default Login:** `admin` / `admin123`

## 📁 Project Structure

```
eresortjava/
├── src/                    # Java backend
│   ├── controller/         # Business logic
│   ├── dao/               # Database access
│   ├── model/             # Data models
│   └── RestServer.java    # REST API
├── frontend/              # React frontend
│   └── src/
│       ├── pages/         # Home, Login, Dashboard, Help
│       ├── utils/         # API client
│       └── styles.css     # Black & blue theme
├── sql/                   # Database schema
└── lib/                   # MySQL connector (download separately)
```

## 🎨 UI Theme

Professional black and dark blue color scheme:
- Background: Pure black with dark blue gradient
- Accent: Dark blue (#1e3a8a) and bright blue (#2563eb)
- Room types: Color-coded (Blue, Green, Amber, Dark Blue)

## 💰 Room Pricing

| Room Type | Price/Night |
|-----------|-------------|
| Single    | LKR 5,000   |
| Double    | LKR 8,000   |
| Family    | LKR 12,000  |
| Suite     | LKR 20,000  |

## 📚 Documentation

- **[SETUP.md](SETUP.md)** - Detailed setup instructions
- **[READY_TO_COMMIT.md](READY_TO_COMMIT.md)** - Git commit guide

## 🔧 API Endpoints

- `POST /api/login` - User authentication
- `GET /api/reservations` - List all reservations
- `POST /api/reservations` - Create reservation
- `GET /api/reservations/{id}` - Get reservation
- `PUT /api/reservations/{id}` - Update reservation
- `DELETE /api/reservations/{id}` - Delete reservation

## 🐛 Troubleshooting

**Backend won't start:**
- Check MySQL is running
- Verify database exists
- Ensure MySQL connector JAR is in `lib/`

**Frontend won't start:**
- Run `npm install` in frontend directory
- Check Node.js version (16+)

**Port conflicts:**
- Backend: 8080
- Frontend: 5174

See [SETUP.md](SETUP.md) for detailed troubleshooting.

## 📦 What to Commit

**Commit these:**
- All `.java` source files
- All frontend source files
- `package.json` and `package-lock.json`
- `sql/schema.sql`
- Documentation files

**Don't commit (in .gitignore):**
- `out/` - Compiled classes
- `lib/` - MySQL connector
- `frontend/node_modules/` - NPM packages
- `*.class`, `*.log` - Build artifacts

See [READY_TO_COMMIT.md](READY_TO_COMMIT.md) for details.

## 🚢 Deployment

1. Clone repository
2. Download MySQL connector
3. Run `npm install`
4. Compile Java code
5. Setup database
6. Run backend and frontend

See [SETUP.md](SETUP.md) for detailed instructions.

## 🛠️ Tech Stack

- **Backend**: Java 11, MySQL 8, JDBC
- **Frontend**: React 18, Vite, Material-UI
- **API**: REST with JSON
- **Database**: MySQL

## 📄 License

Educational project for resort management system.

## 👥 Support

For setup help, see documentation files or create an issue.

---

**Version:** 1.0  
**Last Updated:** 2026-03-06
