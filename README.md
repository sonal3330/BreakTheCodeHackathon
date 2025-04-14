# BreakTheCodeHackathon

## 📌 Project Overview

**BreakTheCodeHackathon** is a Test Automation Framework built using **Java**, **TestNG**, **Selenium**, **Log4j2**, and **Extent Reports**. It focuses on modular design, reusability, clean reporting, secure credentials, and scalable test execution.

---

## 🚀 Features

- ✅ TestNG-based test framework  
- ✅ Selenium WebDriver for browser automation  
- ✅ Log4j2 for logging (INFO & DEBUG logs)  
- ✅ Extent Reports for rich HTML reports  
- ✅ Retry logic for failed tests  
- ✅ Parallel execution support  
- ✅ Secure config using `.properties` files  
- ✅ Clean and maintainable codebase  

---

## 🛠 Tech Stack

- Java 8+  
- Selenium WebDriver  
- TestNG  
- Log4j2  
- Extent Reports  
- Maven  

---

## 🧪 How to Run

### 1️⃣ Prerequisites

- Java 8 or above installed  
- Maven installed and added to PATH  
- Chrome browser and compatible ChromeDriver  
- Git (optional but recommended)  

### 2️⃣ Clone Repository

```bash
git clone https://github.com/sonal3330/BreakTheCodeHackathon.git
cd BreakTheCodeHackathon
```

### 3️⃣ Install Dependencies

```bash
mvn clean install
```

### 4️⃣ Configure Credentials

Add `src/main/java/com/hackathon/config/config.properties`file

```properties

loginUrl: https://the-internet.herokuapp.com/login 
alertUrl: https://the-internet.herokuapp.com/javascript_alerts
username: your_username
password: your_password

# LambdaTest credentials
lambdatest.username: your_username
lambdatest.accesskey: your_accesskey
lambdatest.hub: https://hub.lambdatest.com/wd/hub

```

> ⚠️ Ensure `config.properties` is added to `.gitignore` to avoid pushing credentials to GitHub.

### 5️⃣ Run Tests

Using TestNG from IDE (e.g., Eclipse or IntelliJ):

- Right-click on `testng.xml` → Run As → TestNG Suite

Or from terminal using Maven:

```bash
mvn test
```

---

## 📊 Reporting

- 📄 HTML Report: `test-output/ExtentReport.html`  
- 🗂 Log File: `logs/BreakTheCode.log`

---

## ⚙️ Parallel Execution

Enabled via TestNG suite:

```xml
<suite name="HackathonBreakTheCode" parallel="classes" thread-count="2">
    <test name="BreakTheCode">
        <classes>
            <class name="com.hackathon.tests.LoginTest"/>
            <class name="com.hackathon.tests.AlertTest"/>
        </classes>
    </test>
</suite>
```

---

## 🧾 Logging

Log4j2 handles logging. Set log levels in `log4j2.xml`:

```xml
<Root level="debug">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="File"/>
</Root>
```

Both `INFO` and `DEBUG` logs are written to `logs/BreakTheCode.log`.

---

## 📂 .gitignore Sample

```gitignore
# Eclipse & Java build
.classpath
.project
.settings/
target/
test-output/

# Logs
logs/

# OS-specific
.DS_Store
Thumbs.db

# Configs
*.properties
```

---

## 🤝 Contributing

Contributions are welcome! Fork the repo and open a pull request with your improvements.

---

## 📄 License

This project is licensed under the MIT License.
