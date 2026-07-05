-- Sample data initialization script for Smart Clinic Management System

-- Insert 5 Patients (Question 20 - requires query displaying exactly 5 records from Patient table)
INSERT INTO patient (name, email, phone_number, password, date_of_birth, created_at) VALUES 
('John Doe', 'john.doe@example.com', '555-0101', 'password123', '1985-04-12', '2026-01-10 08:30:00'),
('Jane Smith', 'jane.smith@example.com', '555-0102', 'securepass', '1990-08-25', '2026-02-15 10:15:00'),
('Robert Johnson', 'robert.j@example.com', '555-0103', 'robpass456', '1978-11-03', '2026-03-20 14:00:00'),
('Emily Davis', 'emily.davis@example.com', '555-0104', 'emily2026', '1995-02-14', '2026-04-05 09:45:00'),
('Michael Brown', 'michael.brown@example.com', '555-0105', 'brownpass', '1982-07-19', '2026-05-12 11:20:00');

-- Insert 3 Doctors
INSERT INTO doctor (name, speciality, email, password, consultation_fee, created_at) VALUES 
('Dr. Sarah Jenkins', 'Cardiology', 'sjenkins@clinic.com', 'docpass1', 200.00, '2026-01-01 08:00:00'),
('Dr. Marcus Vance', 'Neurology', 'mvance@clinic.com', 'docpass2', 250.00, '2026-01-01 08:00:00'),
('Dr. Elena Rostova', 'Pediatrics', 'erostova@clinic.com', 'docpass3', 150.00, '2026-01-01 08:00:00');

-- Insert available times for doctors
INSERT INTO doctor_available_times (doctor_id, available_time) VALUES 
(1, '09:00'), (1, '10:00'), (1, '11:00'), (1, '14:00'), (1, '15:00'), (1, '16:00'),
(2, '09:30'), (2, '10:30'), (2, '14:30'), (2, '15:30'),
(3, '09:00'), (3, '10:00'), (3, '11:00'), (3, '13:00'), (3, '14:00');

-- Insert Appointments across various dates in 2026
INSERT INTO appointment (doctor_id, patient_id, appointment_time, status, reason, created_at) VALUES 
(1, 1, '2026-07-05 10:00:00', 'SCHEDULED', 'Annual cardiology checkup', '2026-07-01 09:00:00'),
(1, 2, '2026-07-05 14:00:00', 'SCHEDULED', 'Blood pressure follow-up', '2026-07-01 10:00:00'),
(1, 3, '2026-07-10 11:00:00', 'SCHEDULED', 'Chest tightness evaluation', '2026-07-02 11:30:00'),
(2, 4, '2026-07-05 10:30:00', 'SCHEDULED', 'Migraine consultation', '2026-07-01 14:00:00'),
(2, 5, '2026-07-15 14:30:00', 'SCHEDULED', 'Neurological assessment', '2026-07-03 15:00:00'),
(3, 1, '2026-08-01 09:00:00', 'SCHEDULED', 'Pediatric vaccination consult', '2026-07-04 08:30:00'),
(1, 4, '2026-08-12 15:00:00', 'SCHEDULED', 'Heart rate monitor review', '2026-07-04 12:00:00'),
(1, 5, '2026-09-05 09:00:00', 'SCHEDULED', 'Routine ECG scan', '2026-07-05 08:00:00');

-- Insert Sample Prescriptions
INSERT INTO prescription (doctor_id, patient_id, appointment_id, medications, dosage, instructions, created_at) VALUES 
(1, 1, 1, 'Atorvastatin 20mg, Aspirin 81mg', '1 tablet daily at bedtime', 'Take with food. Avoid grapefruit juice.', '2026-07-05 10:30:00'),
(2, 4, 4, 'Sumatriptan 50mg', '1 tablet at onset of migraine', 'Do not exceed 200mg per day.', '2026-07-05 11:00:00');
