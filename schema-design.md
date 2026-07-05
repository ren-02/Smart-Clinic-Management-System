# Smart Clinic Management System - MySQL Database Schema Design

This document describes the relational database architecture and schema design for the **Smart Clinic Management System**. The schema is optimized for MySQL and uses InnoDB tables with strict relational integrity, proper data types, indexing, and foreign key constraints.

---

## 📊 Entity Relationship Diagram (ERD)

```mermaid
erDiagram
    PATIENT ||--o{ APPOINTMENT : books
    PATIENT ||--o{ PRESCRIPTION : receives
    DOCTOR ||--o{ APPOINTMENT : conducts
    DOCTOR ||--o{ PRESCRIPTION : issues
    DOCTOR ||--o{ DOCTOR_AVAILABLE_TIMES : has
    APPOINTMENT ||--o| PRESCRIPTION : generates

    PATIENT {
        bigint id PK "Primary Key, Auto Increment"
        varchar(100) name "Full Name"
        varchar(120) email "Unique Email Address"
        varchar(20) phone_number "Unique Phone Number"
        varchar(255) password "Hashed Password"
        date date_of_birth "Patient Date of Birth"
        datetime created_at "Registration Timestamp"
    }

    DOCTOR {
        bigint id PK "Primary Key, Auto Increment"
        varchar(100) name "Doctor Full Name"
        varchar(100) speciality "Medical Specialization"
        varchar(120) email "Unique Email Address"
        varchar(255) password "Hashed Password"
        decimal(10,2) consultation_fee "Fee per Consultation"
        datetime created_at "Registration Timestamp"
    }

    DOCTOR_AVAILABLE_TIMES {
        bigint doctor_id FK "Foreign Key to DOCTOR"
        varchar(20) available_time "Time Slot (e.g., 09:00, 10:30)"
    }

    APPOINTMENT {
        bigint id PK "Primary Key, Auto Increment"
        bigint doctor_id FK "Foreign Key to DOCTOR"
        bigint patient_id FK "Foreign Key to PATIENT"
        datetime appointment_time "Scheduled Date and Time"
        varchar(30) status "Status (SCHEDULED, COMPLETED, CANCELLED)"
        varchar(255) reason "Consultation Reason / Notes"
        datetime created_at "Booking Timestamp"
    }

    PRESCRIPTION {
        bigint id PK "Primary Key, Auto Increment"
        bigint doctor_id FK "Foreign Key to DOCTOR"
        bigint patient_id FK "Foreign Key to PATIENT"
        bigint appointment_id FK "Foreign Key to APPOINTMENT"
        text medications "Prescribed Medications List JSON/Text"
        varchar(100) dosage "Dosage frequency and duration"
        text instructions "Doctor instructions and notes"
        datetime created_at "Prescription Timestamp"
    }
```

---

## 🛠️ MySQL CREATE TABLE Scripts

### 1. `patient` Table
Stores registered patient demographic details and authentication credentials.
```sql
CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_patient_email (email),
    INDEX idx_patient_phone (phone_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2. `doctor` Table
Stores healthcare professional credentials, specializations, and fee schedules.
```sql
CREATE TABLE IF NOT EXISTS doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    consultation_fee DECIMAL(10, 2) DEFAULT 150.00,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_doctor_speciality (speciality),
    INDEX idx_doctor_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 3. `doctor_available_times` Table (JPA ElementCollection)
Stores available daily time slots for each doctor, mapped as a dependent collection table.
```sql
CREATE TABLE IF NOT EXISTS doctor_available_times (
    doctor_id BIGINT NOT NULL,
    available_time VARCHAR(20) NOT NULL,
    PRIMARY KEY (doctor_id, available_time),
    CONSTRAINT fk_doctor_times 
        FOREIGN KEY (doctor_id) 
        REFERENCES doctor(id) 
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4. `appointment` Table
Manages consultation schedules, linking patients and doctors with time validations and status tracking.
```sql
CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    reason VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_doctor 
        FOREIGN KEY (doctor_id) 
        REFERENCES doctor(id) 
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointment_patient 
        FOREIGN KEY (patient_id) 
        REFERENCES patient(id) 
        ON DELETE RESTRICT,
    INDEX idx_appointment_doctor_time (doctor_id, appointment_time),
    INDEX idx_appointment_patient (patient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 5. `prescription` Table
Records electronic medical prescriptions generated during or after patient appointments.
```sql
CREATE TABLE IF NOT EXISTS prescription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_id BIGINT,
    medications TEXT NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    instructions TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_doctor 
        FOREIGN KEY (doctor_id) 
        REFERENCES doctor(id) 
        ON DELETE RESTRICT,
    CONSTRAINT fk_prescription_patient 
        FOREIGN KEY (patient_id) 
        REFERENCES patient(id) 
        ON DELETE RESTRICT,
    CONSTRAINT fk_prescription_appointment 
        FOREIGN KEY (appointment_id) 
        REFERENCES appointment(id) 
        ON DELETE SET NULL,
    INDEX idx_prescription_patient (patient_id),
    INDEX idx_prescription_doctor (doctor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## 🔍 Key Relational Design Highlights
1. **Foreign Key Integrity**: All foreign keys (`doctor_id`, `patient_id`, `appointment_id`) enforce referential integrity using `FOREIGN KEY` constraints. Deletions of doctors or patients with active appointments are restricted (`ON DELETE RESTRICT`) to prevent orphaned medical records.
2. **JPA ElementCollection Mapping**: The `doctor_available_times` table models a clean many-to-one join table without needing a standalone entity class, perfectly aligning with JPA `@ElementCollection` standards.
3. **Optimized Indexing**: Secondary indexes are placed on frequently queried fields (`email`, `phone_number`, `speciality`, and composite index on `doctor_id, appointment_time`) to ensure lightning-fast execution of custom JPA repository queries and stored procedures.
