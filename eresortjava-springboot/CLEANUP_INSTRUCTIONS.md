# Cleanup Instructions - After Spring Boot is Confirmed Working

## ⚠️ IMPORTANT: Only run this AFTER you've tested Spring Boot thoroughly!

---

## 🧪 Pre-Cleanup Checklist

Before cleaning up, verify Spring Boot works:

- [ ] Maven installed and working (`mvn -version`)
- [ ] Spring Boot builds successfully (`mvn clean package`)
- [ ] Spring Boot runs without errors (`mvn spring-boot:run`)
- [ ] Backend accessible at http://localhost:8080
- [ ] Frontend connects to Spring Boot backend
- [ ] Login works (admin/admin123)
- [ ] Create reservation works
- [ ] View reservations works
- [ ] Edit reservation works
- [ ] Delete reservation works
- [ ] Search works (by name, room type, dates)
- [ ] Bill generation works
- [ ] All features tested and working ✅

---

## 🗑️ What to Remove (Pure Java Backend)

### Files to DELETE:
```
eresortjava/
├── src/                          ❌ DELETE (pure Java backend)
│   ├── Main.java
│   ├── RestServer.java
│   ├── DbInspect.java
│   ├── controller/
│   ├── dao/
│   ├── model/
│   ├── view/
│   └── test/
├── out/                          ❌ DELETE (compiled classes)
└── lib/                          ❌ DELETE (MySQL connector JAR)
```

### Files to KEEP:
```
eresortjava/
├── frontend/                     ✅ KEEP (React app)
├── sql/                          ✅ KEEP (database schema)
├── Report/                       ✅ KEEP (documentation)
├── report images/                ✅ KEEP (UML diagrams)
├── README.md                     ✅ KEEP (update to point to Spring Boot)
├── SETUP.md                      ✅ KEEP (update instructions)
└── .gitignore                    ✅ KEEP
```

---

## 🔄 Option 1: Replace Pure Java with Spring Boot

### Manual Steps:

1. **Backup first** (just in case):
   ```powershell
   cd "C:\Users\ArkamRameez\Downloads\eresortjava S"
   Copy-Item -Path "eresortjava" -Destination "eresortjava-backup" -Recurse
   ```

2. **Delete pure Java backend**:
   ```powershell
   cd eresortjava
   Remove-Item -Path "src" -Recurse -Force
   Remove-Item -Path "out" -Recurse -Force
   Remove-Item -Path "lib" -Recurse -Force
   ```

3. **Move Spring Boot code**:
   ```powershell
   # Copy Spring Boot src to main project
   Copy-Item -Path "../eresortjava-springboot/src" -Destination "." -Recurse
   Copy-Item -Path "../eresortjava-springboot/pom.xml" -Destination "."
   
   # Copy Spring Boot docs
   Copy-Item -Path "../eresortjava-springboot/README.md" -Destination "README-SPRINGBOOT.md"
   Copy-Item -Path "../eresortjava-springboot/run.bat" -Destination "."
   Copy-Item -Path "../eresortjava-springboot/run.sh" -Destination "."
   ```

4. **Update main README.md** to reflect Spring Boot

---

## 🔄 Option 2: Keep Both Versions (Recommended for Learning)

Keep both versions in separate folders:

```
eresortjava/
├── frontend/                     # React app (shared)
├── sql/                          # Database (shared)
├── backend-pure-java/            # Original pure Java
│   ├── src/
│   ├── out/
│   └── lib/
├── backend-springboot/           # New Spring Boot
│   ├── src/
│   └── pom.xml
├── Report/                       # Documentation
└── README.md                     # Updated to explain both
```

### Steps for Option 2:
```powershell
cd eresortjava

# Rename pure Java backend
Rename-Item -Path "src" -NewName "backend-pure-java"

# Move Spring Boot
Copy-Item -Path "../eresortjava-springboot/src" -Destination "backend-springboot/src" -Recurse
Copy-Item -Path "../eresortjava-springboot/pom.xml" -Destination "backend-springboot/"
```

---

## 📝 Update README.md

After cleanup, update the main README.md:

```markdown
# Ocean View Resort - Spring Boot Edition

## Backend: Spring Boot 3.2

The backend has been refactored to Spring Boot for production use.

### Run Backend:
```bash
cd backend-springboot  # or just root if you chose Option 1
mvn spring-boot:run
```

### Run Frontend:
```bash
cd frontend
npm run dev
```

### Access:
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Login: admin / admin123
```

---

## 🧹 Automated Cleanup Script (Option 1)

Save this as `cleanup-pure-java.bat`:

```batch
@echo off
echo ========================================
echo   Cleanup Pure Java Backend
echo ========================================
echo.
echo This will DELETE the pure Java backend code!
echo Make sure Spring Boot is working first!
echo.
pause

echo Deleting pure Java backend...
rmdir /s /q src
rmdir /s /q out
rmdir /s /q lib

echo.
echo Moving Spring Boot code...
xcopy /s /e /i ..\eresortjava-springboot\src src
copy ..\eresortjava-springboot\pom.xml .
copy ..\eresortjava-springboot\run.bat .

echo.
echo ✅ Cleanup complete!
echo.
echo Next steps:
echo 1. Update README.md
echo 2. Test: mvn spring-boot:run
echo 3. Delete eresortjava-springboot folder if everything works
pause
```

---

## ✅ Post-Cleanup Verification

After cleanup, verify:

1. **Build works**:
   ```powershell
   mvn clean package
   ```

2. **Run works**:
   ```powershell
   mvn spring-boot:run
   ```

3. **Frontend connects**:
   - Start frontend: `cd frontend && npm run dev`
   - Test all features

4. **Git status** (if using Git):
   ```powershell
   git status
   git add .
   git commit -m "Refactor: Replace pure Java backend with Spring Boot"
   ```

---

## 🎯 Final Structure (Option 1)

```
eresortjava/
├── src/                          # Spring Boot backend
│   ├── main/
│   │   ├── java/com/oceanview/eresort/
│   │   └── resources/
│   └── test/
├── frontend/                     # React app
├── sql/                          # Database
├── Report/                       # Documentation
├── pom.xml                       # Maven (Spring Boot)
├── run.bat                       # Startup script
└── README.md                     # Updated
```

---

## 📞 If Something Goes Wrong

If Spring Boot doesn't work after cleanup:

1. **Restore from backup**:
   ```powershell
   Remove-Item -Path "eresortjava" -Recurse -Force
   Copy-Item -Path "eresortjava-backup" -Destination "eresortjava" -Recurse
   ```

2. **Check the issue**:
   - Maven installed? `mvn -version`
   - Java 17? `java -version`
   - MySQL running? `mysql -u root -p`
   - DB_PASS set? `echo $env:DB_PASS`

3. **Review logs**:
   - Check console output
   - Check `application.properties`

---

## 🎉 Success Criteria

You're done when:
- ✅ Spring Boot backend runs without errors
- ✅ Frontend connects and works
- ✅ All features tested and working
- ✅ Pure Java code removed (if desired)
- ✅ Documentation updated
- ✅ Project structure clean

---

**⚠️ Remember: Only proceed with cleanup AFTER thorough testing!**
