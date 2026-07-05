-- Schema initialization script for Smart Clinic Management System

CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    consultation_fee DECIMAL(10, 2) DEFAULT 150.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctor_available_times (
    doctor_id BIGINT NOT NULL,
    available_time VARCHAR(20) NOT NULL,
    PRIMARY KEY (doctor_id, available_time),
    CONSTRAINT fk_doctor_times FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_app_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS prescription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_id BIGINT,
    medications CLOB NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    instructions CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pres_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_pres_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
    CONSTRAINT fk_pres_app FOREIGN KEY (appointment_id) REFERENCES appointment(id)
);
