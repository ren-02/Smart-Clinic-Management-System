# 🏥 Smart Clinic Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![JJWT](https://img.shields.io/badge/JJWT-0.12.5-red.svg?style=flat-square)](https://github.com/jwtk/jjwt)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

An enterprise-grade, full-stack medical clinic management platform built with **Spring Boot 3**, **Spring Data JPA**, **MySQL**, and **JSON Web Tokens (JJWT 0.12.5)**. Designed with modern software engineering practices, this capstone system provides role-based workflows for Administrators, Doctors, and Patients, featuring secure JWT authentication, dynamic appointment scheduling, e-prescriptions, custom MySQL stored procedures, and glassmorphism-styled web portals.

---

## 🌟 Key Features

* 🔐 **Role-Based Security & JWT Auth**: Stateless authentication using modern JJWT 0.12.5 (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`) with 512-bit encryption for Admin, Doctor, and Patient roles.
* 👨‍⚕️ **Doctor & Speciality Directory**: Real-time availability tracking with `@ElementCollection` time chips, fee management, and consultation room assignments.
* 📅 **Dynamic Appointment Scheduling**: Integrated booking system preventing double-booking, supporting status transitions (`SCHEDULED`, `COMPLETED`, `CANCELLED`).
* 💊 **E-Prescription & Medical Records**: Digital prescription issuance linking diagnosis, medications, dosage instructions, and consultation history directly to patient profiles.
* ⚡ **Custom MySQL Stored Procedures**: Highly optimized server-side reporting procedures for clinic analytics:
  * `sp_GetDoctorAppointments`: Retrieves chronological patient rosters for specific doctors.
  * `sp_GetPatientPrescriptions`: Generates comprehensive medical consultation histories.
  * `sp_GetDoctorRevenue`: Calculates total generated practice revenue and completed appointment volume.
* 🖥️ **Responsive Role Portals**: Modern glassmorphism web interfaces for executive control, physician consultation, and patient self-service booking.

---

## 📁 Project Structure & Documentation

This repository is organized and structured to meet enterprise engineering standards:

| File / Folder | Description |
| :--- | :--- |
| **[`ANSWERS.md`](./ANSWERS.md)** | Complete capstone submission report containing repository links, MySQL logs, stored procedure outputs, and REST API curl verification. |
| **[`issues.md`](./issues.md)** | Agile user stories formatted as `As a [Role], I want [Feature] so that [Benefit]` with acceptance criteria checklists for all personas. |
| **[`schema-design.md`](./schema-design.md)** | Relational schema specifications, ER diagram, exact data types, and foreign key constraints (`ON DELETE RESTRICT` / `CASCADE`). |
| **[`stored-procedures.sql`](./stored-procedures.sql)** | Complete SQL creation scripts and test executions for custom clinic reporting stored procedures. |
| **[`src/main/java/`](./src/main/java)** | Core Spring Boot application source code (`model`, `repository`, `service`, `controller`, `security`, `dto`). |
| **[`src/main/resources/static/`](./src/main/resources/static)** | Interactive UI web portals (`admin-login.html`, `doctor-appointments.html`, `patient-search-doctor.html`, etc.). |
| **[`Dockerfile`](./Dockerfile)** | Optimized containerization recipe for cloud deployment and isolated local execution. |

---

## 🛠️ Technology Stack

* **Backend Framework**: Java 21, Spring Boot 3.2.5 (Web, Data JPA, Validation, Test)
* **Database & ORM**: MySQL 8.0, Hibernate / Spring Data JPA, H2 Database (for embedded testing)
* **Security & Tokens**: JSON Web Tokens (io.jsonwebtoken JJWT v0.12.5)
* **Frontend UI**: Vanilla HTML5, CSS3 Glassmorphism, Responsive Grid/Flexbox
* **Build & DevOps**: Apache Maven wrapper (`mvnw`), Docker containerization

---

## 🚀 Quick Start & Installation

### 1. Prerequisites
* **Java Development Kit (JDK) 21** installed and configured in your PATH.
* **Maven 3.8+** (or use the included `./mvnw` wrapper).
* **MySQL 8.0+** running locally on default port `3306` (or Docker).

### 2. Clone the Repository
```bash
git clone https://github.com/vince/smart-clinic-management-system.git
cd smart-clinic-management-system
```

### 3. Database Configuration
Create the MySQL database:
```sql
CREATE DATABASE smart_clinic_db;
```
Update your database credentials in `src/main/resources/application.properties` (or set via environment variables):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_clinic_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 4. Build & Run Locally
Using the included Maven wrapper:
```bash
# On Windows (Command Prompt or PowerShell)
.\mvnw.cmd clean package -DskipTests
.\mvnw.cmd spring-boot:run

# On Linux / macOS
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```
The REST API server and web portals will launch at `http://localhost:8080`.

---

## 🐳 Running with Docker

You can easily build and deploy the clinic system inside an isolated container:

```bash
# 1. Build the Docker image
docker build -t smart-clinic-management-system:latest .

# 2. Run the container on port 8080
docker run -d -p 8080:8080 --name smart-clinic-app smart-clinic-management-system:latest
```

---

## 🔌 Core REST API Endpoints

| HTTP Method | Endpoint | Description | Role Required |
| :---: | :--- | :--- | :---: |
| `POST` | `/api/auth/login` | Authenticate user and receive 512-bit JWT bearer token | Public |
| `GET` | `/api/doctors/availability` | Query doctors by speciality, date, and time slot | Public / Patient |
| `POST` | `/api/doctors` | Register a new physician with consultation schedule and fees | Admin |
| `POST` | `/api/appointments/book` | Schedule a new patient appointment slot | Patient / Admin |
| `GET` | `/api/appointments/doctor/{id}` | Retrieve all appointments assigned to a specific physician | Doctor / Admin |
| `POST` | `/api/prescriptions` | Issue a digital prescription for a completed appointment | Doctor |
| `GET` | `/api/prescriptions/patient/{id}` | Fetch medical history and prescriptions for a patient | Patient / Doctor |

---

## 📄 License

This project is licensed under the MIT License. Developed as a comprehensive capstone project demonstrating enterprise full-stack Spring Boot architecture.
