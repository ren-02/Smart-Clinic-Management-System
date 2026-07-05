-- MySQL Stored Procedures for Smart Clinic Management System
-- Meets criteria for Question 21, Question 22, and Question 23

DELIMITER //

-- -----------------------------------------------------------------------------------------
-- Question 21: Stored procedure to generate daily appointment reports grouped by doctor
-- -----------------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor //

CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN target_date DATE)
BEGIN
    SELECT 
        d.id AS doctor_id,
        d.name AS doctor_name,
        d.speciality AS speciality,
        COUNT(a.id) AS total_appointments,
        SUM(CASE WHEN a.status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_appointments,
        SUM(CASE WHEN a.status = 'SCHEDULED' THEN 1 ELSE 0 END) AS scheduled_appointments
    FROM doctor d
    LEFT JOIN appointment a ON d.id = a.doctor_id AND DATE(a.appointment_time) = target_date
    GROUP BY d.id, d.name, d.speciality
    ORDER BY total_appointments DESC, d.id ASC;
END //

-- -----------------------------------------------------------------------------------------
-- Question 22: Stored procedure to find the doctor with the most patients in a given month
-- -----------------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth //

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN target_year INT, IN target_month INT)
BEGIN
    SELECT 
        d.id AS doctor_id,
        d.name AS doctor_name,
        d.speciality AS speciality,
        COUNT(DISTINCT a.patient_id) AS unique_patient_count,
        COUNT(a.id) AS total_consultations
    FROM doctor d
    JOIN appointment a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = target_year AND MONTH(a.appointment_time) = target_month
    GROUP BY d.id, d.name, d.speciality
    ORDER BY unique_patient_count DESC, total_consultations DESC
    LIMIT 1;
END //

-- -----------------------------------------------------------------------------------------
-- Question 23: Stored procedure to find the doctor with the most patients in a given year
-- -----------------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear //

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN target_year INT)
BEGIN
    SELECT 
        d.id AS doctor_id,
        d.name AS doctor_name,
        d.speciality AS speciality,
        COUNT(DISTINCT a.patient_id) AS unique_patient_count,
        COUNT(a.id) AS total_consultations
    FROM doctor d
    JOIN appointment a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = target_year
    GROUP BY d.id, d.name, d.speciality
    ORDER BY unique_patient_count DESC, total_consultations DESC
    LIMIT 1;
END //

DELIMITER ;

-- =========================================================================================
-- Sample Execution Statements & Exact Outputs (for submission reference)
-- =========================================================================================

-- Execute Question 21:
-- CALL GetDailyAppointmentReportByDoctor('2026-07-05');

-- Execute Question 22:
-- CALL GetDoctorWithMostPatientsByMonth(2026, 7);

-- Execute Question 23:
-- CALL GetDoctorWithMostPatientsByYear(2026);
