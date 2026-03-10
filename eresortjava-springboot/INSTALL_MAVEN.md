# Maven Installation Guide for Windows

## 📥 Quick Install Steps

### Method 1: Manual Installation (Recommended)

#### Step 1: Download Maven
1. Open browser: https://maven.apache.org/download.cgi
2. Download: **apache-maven-3.9.6-bin.zip**
3. Save to your Downloads folder

#### Step 2: Extract Maven
1. Open File Explorer
2. Navigate to Downloads folder
3. Right-click `apache-maven-3.9.6-bin.zip`
4. Select "Extract All..."
5. Extract to: `C:\Program Files\`
6. Result: `C:\Program Files\apache-maven-3.9.6\`

#### Step 3: Set Environment Variables

**Option A: Using PowerShell (Run as Administrator)**
```powershell
# Set MAVEN_HOME
[System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\apache-maven-3.9.6", "Machine")

# Get current PATH
$currentPath = [System.Environment]::GetEnvironmentVariable("Path", "Machine")

# Add Maven to PATH
$newPath = $currentPath + ";C:\Program Files\apache-maven-3.9.6\bin"
[System.Environment]::SetEnvironmentVariable("Path", $newPath, "Machine")

Write-Host "✅ Maven environment variables set!"
Write-Host "⚠️  Please restart PowerShell for changes to take effect"
```

**Option B: Using GUI**
1. Press `Win + X` → Select "System"
2. Click "Advanced system settings"
3. Click "Environment Variables"
4. Under "System variables":
   - Click "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\apache-maven-3.9.6`
   - Click OK
5. Find "Path" in System variables
   - Click "Edit"
   - Click "New"
   - Add: `C:\Program Files\apache-maven-3.9.6\bin`
   - Click OK
6. Click OK on all dialogs

#### Step 4: Verify Installation
1. **Close and reopen PowerShell** (important!)
2. Run:
```powershell
mvn -version
```

You should see:
```
Apache Maven 3.9.6
Maven home: C:\Program Files\apache-maven-3.9.6
Java version: 17.0.17
```

---

### Method 2: Using Chocolatey (If you have it)

```powershell
# Install Chocolatey first (if not installed)
Set-ExecutionPolicy Bypass -Scope Process -Force
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Then install Maven
choco install maven -y

# Verify
mvn -version
```

---

### Method 3: Using Scoop (Alternative Package Manager)

```powershell
# Install Scoop
iwr -useb get.scoop.sh | iex

# Install Maven
scoop install maven

# Verify
mvn -version
```

---

## 🧪 After Installation - Test Spring Boot

Once Maven is installed and verified:

```powershell
cd eresortjava-springboot

# Clean and build
mvn clean package

# Run Spring Boot
mvn spring-boot:run
```

---

## ❌ Troubleshooting

### Problem: "mvn is not recognized"
**Solution:** 
- Restart PowerShell (close and reopen)
- Check PATH: `$env:Path -split ';' | Select-String maven`
- Verify folder exists: `Test-Path "C:\Program Files\apache-maven-3.9.6\bin\mvn.cmd"`

### Problem: "JAVA_HOME not set"
**Solution:**
```powershell
# Set JAVA_HOME
[System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.17+10", "Machine")
```

### Problem: Maven downloads are slow
**Solution:** Maven downloads dependencies on first run. This is normal and only happens once.

---

## 📝 Quick Reference

### Maven Commands
```powershell
# Check version
mvn -version

# Clean build
mvn clean

# Compile
mvn compile

# Run tests
mvn test

# Package (create JAR)
mvn package

# Run Spring Boot
mvn spring-boot:run

# Clean and package
mvn clean package
```

---

## ✅ Success Checklist

- [ ] Maven downloaded
- [ ] Maven extracted to C:\Program Files
- [ ] MAVEN_HOME environment variable set
- [ ] Maven bin added to PATH
- [ ] PowerShell restarted
- [ ] `mvn -version` works
- [ ] Ready to run Spring Boot!

---

## 🚀 Next Steps After Maven Install

1. **Build the project:**
   ```powershell
   cd eresortjava-springboot
   mvn clean package
   ```

2. **Run Spring Boot:**
   ```powershell
   mvn spring-boot:run
   ```

3. **Access application:**
   - Backend: http://localhost:8080
   - Frontend: http://localhost:5173 (start separately)
   - Login: admin / admin123

---

## 📞 Still Having Issues?

If Maven installation fails, you can:
1. Use IDE (IntelliJ IDEA or Eclipse) - they have built-in Maven
2. Download pre-built JAR (I can create one for you)
3. Use Docker (if you have Docker installed)

---

**Installation Time:** ~5-10 minutes  
**First Build Time:** ~2-5 minutes (downloads dependencies)  
**Subsequent Builds:** ~10-30 seconds
