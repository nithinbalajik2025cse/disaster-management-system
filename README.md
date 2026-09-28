# Smart Disaster Relief Resource Allocation & Inventory Tracker

A clean, production-grade Spring Boot 3.x web application designed for disaster relief management organizations. The system features a deterministic **Smart Priority-Based Resource Allocation Engine** powered by Java's `PriorityQueue` data structure.

---

## 🌟 Key Features

1. **Smart Priority-Based Resource Allocation Engine**:
   - Evaluates pending requests across disaster zones.
   - Calculates priority scores using real-time emergency metrics.
   - Uses `java.util.PriorityQueue` to dynamically allocate limited inventory to the highest-priority areas first.
   - Supports full and partial allocation with automatic inventory deduction.

2. **Disaster Management Module**:
   - Track disaster types (FLOOD, EARTHQUAKE, CYCLONE, LANDSLIDE, DROUGHT, FIRE, TSUNAMI).
   - Categorize severity by Priority Levels (CRITICAL, HIGH, MEDIUM, LOW).
   - Monitor affected and critical population counts.

3. **Resource Inventory & Low Stock Tracking**:
   - Manage relief supplies (Food, Water, Medicine, Tents, First Aid, Clothes).
   - Automatic low-stock warnings when `quantity <= minimumStock`.

4. **Volunteer & Donation Tracker**:
   - Manage field volunteers, skill sets, availability, and assigned disaster zones.
   - Record monetary and material donations.

5. **Role-Based Authentication**:
   - Pre-configured roles: `ADMIN`, `COORDINATOR`, `VOLUNTEER`.

6. **Interactive Responsive Frontend**:
   - Modern Glassmorphism UI built with HTML5, CSS3, JavaScript, and Thymeleaf.
   - Live **"RUN SMART ALLOCATION"** action button.

---

## 🧠 Smart Priority Allocation Algorithm

### Priority Formula

$$\text{Priority Score} = (\text{Priority Weight} \times 40) + (\text{Critical People} \times 30) + (\text{Affected People} \times 20) + (\text{Waiting Hours} \times 10)$$

Where Priority Level Weights are:
- `CRITICAL` = 4
- `HIGH` = 3
- `MEDIUM` = 2
- `LOW` = 1

### Algorithm Workflow
```text
Pending Requests
       ↓
Calculate Priority Score
       ↓
Insert into PriorityQueue (Max-Heap by Priority Score)
       ↓
Poll Highest Priority Request
       ↓
Check Warehouse Inventory
       ↓
Allocate Full or Partial Quantity
       ↓
Deduct Inventory & Log Allocation Audit Record
```

---

## 🏗️ Technology Stack

- **Java**: 17 / 21
- **Framework**: Spring Boot 3.3.4 (Spring Web, Spring Data JPA)
- **Database**: MySQL (`disaster_relief_db`) / H2 Memory Database
- **ORM**: Hibernate
- **Build Tool**: Apache Maven
- **Frontend**: HTML5, CSS3 (Vanilla), JavaScript, Thymeleaf
- **Testing**: JUnit 5, Spring Boot Test

---

## 📂 Layered Project Structure

```text
disaster-relief/
│
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java
│   │   │   ├── config/
│   │   │   │   └── DatabaseConfig.java
│   │   │   ├── controllers/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── DisasterController.java
│   │   │   │   ├── ResourceController.java
│   │   │   │   ├── ResourceRequestController.java
│   │   │   │   ├── AllocationController.java
│   │   │   │   ├── VolunteerController.java
│   │   │   │   ├── DonationController.java
│   │   │   │   ├── DistributionController.java
│   │   │   │   └── ReportController.java
│   │   │   ├── models/
│   │   │   │   ├── User.java, Disaster.java, Resource.java, ResourceRequest.java
│   │   │   │   ├── Allocation.java, Volunteer.java, Donation.java, Distribution.java
│   │   │   │   └── Enums (PriorityLevel, RequestStatus, UserRole, etc.)
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── exceptions/
│   │   └── resources/
│   │       ├── static/ (css/style.css, js/app.js)
│   │       ├── templates/ (dashboard.html, disasters.html, resources.html, requests.html, etc.)
│   │       └── application.properties
│   └── test/
│       └── java/com/example/demo/DemoApplicationTests.java
```

---

## 🚀 Quick Start & Execution

### 1. Database Setup (MySQL)
Ensure MySQL is running on `localhost:3306`. The database `disaster_relief_db` will be auto-created upon startup.

Default configuration in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/disaster_relief_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password
```

### 2. Run Application
Run the Spring Boot application using Maven:

```powershell
mvn spring-boot:run
```

Access the application in your browser:
```text
http://localhost:8080
```

### 3. Demo User Credentials
- **ADMIN**: `admin` / `admin123`
- **COORDINATOR**: `coord1` / `coord123`
- **VOLUNTEER**: `vol1` / `vol123`

### 4. Run Automated Tests
```powershell
mvn clean test
```

---

## 🔌 REST API Endpoints Summary

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/login` | User Login & Authentication |
| `GET` / `POST` | `/api/disasters` | Retrieve & Register Disasters |
| `GET` / `POST` | `/api/resources` | Retrieve & Register Relief Supplies |
| `GET` / `POST` | `/api/requests` | Retrieve & Submit Resource Requests |
| `POST` | `/api/allocations/run` | **Execute Smart Priority Allocation Engine** |
| `GET` / `POST` | `/api/volunteers` | Retrieve & Register Volunteers |
| `GET` / `POST` | `/api/donations` | Retrieve & Log Donations |
| `GET` | `/api/reports/dashboard` | Get Summary Statistics |
