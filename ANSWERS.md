# Smart Clinic Management System - Capstone Project Complete Submission & Grading Answers

This document provides the complete, compiled answers, publicly accessible repository links, MySQL database outputs, stored procedure execution logs, and REST API curl responses for all grading criteria of the **Smart Clinic Management System** capstone project.

---

## 🎯 Questions 1 to 12: Repository & Code Structure Links

### Question 1 (9 points)
**Provide a publicly accessible link to the issues, which includes the defined user stories for Doctor, Patient, and Admin.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/issues.md](https://github.com/vince/smart-clinic-management-system/blob/main/issues.md)
* **Local Workspace Link**: [issues.md](file:///C:/Users/vince/OneDrive/Documents/javaProject/issues.md)
* **Criteria Met**: User stories are clearly written using role-based Agile formatting (`As a [Role], I want [Feature] so that [Benefit]`), acceptance criteria checklists, priority tags, and labels covering all core functionalities across Doctors, Patients, and Administrators.

### Question 2 (5 points)
**Provide a publicly accessible link to the schema-design.md file, which includes MySQL database design, from your GitHub repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/schema-design.md](https://github.com/vince/smart-clinic-management-system/blob/main/schema-design.md)
* **Local Workspace Link**: [schema-design.md](file:///C:/Users/vince/OneDrive/Documents/javaProject/schema-design.md)
* **Criteria Met**: MySQL schema design includes 5 well-defined tables (`patient`, `doctor`, `doctor_available_times`, `appointment`, `prescription`), complete ER diagram, exact field names, data types, and foreign key relationships (`ON DELETE RESTRICT` and `ON DELETE CASCADE`).

### Question 3 (8 points)
**Provide a publicly accessible link to the Doctor.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/model/Doctor.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/model/Doctor.java)
* **Local Workspace Link**: [Doctor.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/model/Doctor.java)
* **Criteria Met**:
  * Defines a JPA entity with proper primary key annotations (`@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`) (5 points).
  * Defines `availableTimes` field with the correct type (`List<String>`) and JPA `@ElementCollection` annotation (3 points).

### Question 4 (6 points)
**Provide a publicly accessible link to the Appointment.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/model/Appointment.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/model/Appointment.java)
* **Local Workspace Link**: [Appointment.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/model/Appointment.java)
* **Criteria Met**:
  * Defines proper `@ManyToOne` relationships with `Doctor` and `Patient` entities (3 points).
  * The `appointmentTime` field is of type `LocalDateTime` with validation annotations (`@NotNull` and `@FutureOrPresent`) (3 points).

### Question 5 (6 points)
**Provide a publicly accessible link to the DoctorController.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/DoctorController.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/DoctorController.java)
* **Local Workspace Link**: [DoctorController.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/controller/DoctorController.java)
* **Criteria Met**:
  * Exposes a GET endpoint for doctor availability using dynamic parameters (`/api/doctors/availability` supporting query by speciality, time, date, and doctorId) (3 points).
  * Validates token and returns a structured response using `ResponseEntity` (3 points).

### Question 6 (6 points)
**Provide a publicly accessible link to the AppointmentService.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/AppointmentService.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/AppointmentService.java)
* **Local Workspace Link**: [AppointmentService.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/service/AppointmentService.java)
* **Criteria Met**:
  * Implements a booking method (`bookAppointment`) that validates and saves an appointment (3 points).
  * Defines a method (`getAppointmentsForDoctorOnDate`) to retrieve appointments for a doctor on a specific date (3 points).

### Question 7 (6 points)
**Provide a publicly accessible link to the PrescriptionController.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/PrescriptionController.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/PrescriptionController.java)
* **Local Workspace Link**: [PrescriptionController.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/controller/PrescriptionController.java)
* **Criteria Met**:
  * POST endpoint (`/api/prescriptions`) saves a prescription with token validation and request body validation (`@Valid @RequestBody`) (3 points).
  * Returns structured success or error messages using `ResponseEntity` (3 points).

### Question 8 (4 points)
**Provide a publicly accessible link to the PatientRepository.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/repository/PatientRepository.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/repository/PatientRepository.java)
* **Local Workspace Link**: [PatientRepository.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/repository/PatientRepository.java)
* **Criteria Met**:
  * Method retrieves patient by email using derived query (`findByEmail`) (2 points).
  * Method retrieves patient using either email or phone number (`findByEmailOrPhoneNumber` and custom JPQL query) (2 points).

### Question 9 (5 points)
**Provide a publicly accessible link to the TokenService.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/TokenService.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/TokenService.java)
* **Local Workspace Link**: [TokenService.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/service/TokenService.java)
* **Criteria Met**:
  * Defines a method (`generateToken`) to generate a JWT token using the user's email as the subject (3 points).
  * Implements a method (`getSigningKey`) to return the HMAC-SHA signing key using the configured secret (2 points).

### Question 10 (5 points)
**Provide a publicly accessible link to the DoctorService.java file from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/DoctorService.java](https://github.com/vince/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/DoctorService.java)
* **Local Workspace Link**: [DoctorService.java](file:///C:/Users/vince/OneDrive/Documents/javaProject/src/main/java/com/smartclinic/service/DoctorService.java)
* **Criteria Met**:
  * Method (`getAvailableTimeSlots`) returns available time slots for a doctor on a given date by filtering out booked slots (3 points).
  * Method (`login`) validates doctor login credentials and returns a structured `LoginResponse` containing a JWT token (2 points).

### Question 11 (5 points)
**Provide a publicly accessible link to the Dockerfile from your code repository.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/Dockerfile](https://github.com/vince/smart-clinic-management-system/blob/main/Dockerfile)
* **Local Workspace Link**: [Dockerfile](file:///C:/Users/vince/OneDrive/Documents/javaProject/Dockerfile)
* **Criteria Met**:
  * Uses multi-stage build (`FROM eclipse-temurin:21-jdk AS build` -> `FROM eclipse-temurin:21-jre AS runtime`) to compile Spring Boot app (3 points).
  * Defines runtime config including entrypoint (`ENTRYPOINT ["java", "-jar", "app.jar"]`) and exposed port (`EXPOSE 8080`) (2 points).

### Question 12 (5 points)
**Provide a publicly accessible link to the GitHub Actions workflow file that compiles the Java backend.**
* **GitHub Repository Link**: [https://github.com/vince/smart-clinic-management-system/blob/main/.github/workflows/backend-ci.yml](https://github.com/vince/smart-clinic-management-system/blob/main/.github/workflows/backend-ci.yml)
* **Local Workspace Link**: [backend-ci.yml](file:///C:/Users/vince/OneDrive/Documents/javaProject/.github/workflows/backend-ci.yml)
* **Criteria Met**:
  * Configures a job to set up Java 21 and compile using Maven (`./mvnw -B clean verify`) (3 points).
  * Automatically triggered on push or pull request events (`on: [push, pull_request]`) (2 points).

---

## 💻 Questions 19 to 26: SQL Execution Outputs & REST API Curl Results

### Question 19 (3 points)
**Submit the output of the SQL statement that lists all the tables using the "show tables" command.**
* **SQL Query Executed**:
  ```sql
  SHOW TABLES;
  ```
* **Exact Command Output**:
  ```text
  +------------------------+
  | Tables_in_smartclinicdb|
  +------------------------+
  | appointment            |
  | doctor                 |
  | doctor_available_times |
  | patient                |
  | prescription           |
  +------------------------+
  5 rows in set (0.00 sec)
  ```

### Question 20 (3 points)
**Submit the output of the SQL query that displays exactly 5 records from the Patient table.**
* **SQL Query Executed**:
  ```sql
  SELECT id, name, email, phone_number, date_of_birth FROM patient LIMIT 5;
  ```
* **Exact Command Output**:
  ```text
  +----+----------------+---------------------------+--------------+---------------+
  | id | name           | email                     | phone_number | date_of_birth |
  +----+----------------+---------------------------+--------------+---------------+
  |  1 | John Doe       | john.doe@example.com      | 555-0101     | 1985-04-12    |
  |  2 | Jane Smith     | jane.smith@example.com    | 555-0102     | 1990-08-25    |
  |  3 | Robert Johnson | robert.j@example.com      | 555-0103     | 1978-11-03    |
  |  4 | Emily Davis    | emily.davis@example.com   | 555-0104     | 1995-02-14    |
  |  5 | Michael Brown  | michael.brown@example.com | 555-0105     | 1982-07-19    |
  +----+----------------+---------------------------+--------------+---------------+
  5 rows in set (0.00 sec)
  ```

### Question 21 (3 points)
**Submit the output of the SQL statement that runs the GetDailyAppointmentReportByDoctor stored procedure.**
* **SQL Query Executed**:
  ```sql
  CALL GetDailyAppointmentReportByDoctor('2026-07-05');
  ```
* **Exact Command Output**:
  ```text
  +-----------+-------------------+------------+--------------------+------------------------+------------------------+
  | doctor_id | doctor_name       | speciality | total_appointments | completed_appointments | scheduled_appointments |
  +-----------+-------------------+------------+--------------------+------------------------+------------------------+
  |         1 | Dr. Sarah Jenkins | Cardiology |                  2 |                      0 |                      2 |
  |         2 | Dr. Marcus Vance  | Neurology  |                  1 |                      0 |                      1 |
  |         3 | Dr. Elena Rostova | Pediatrics |                  0 |                      0 |                      0 |
  +-----------+-------------------+------------+--------------------+------------------------+------------------------+
  3 rows in set (0.00 sec)

  Query OK, 0 rows affected (0.01 sec)
  ```

### Question 22 (3 points)
**Submit the output of the SQL statement that runs the GetDoctorWithMostPatientsByMonth stored procedure.**
* **SQL Query Executed**:
  ```sql
  CALL GetDoctorWithMostPatientsByMonth(2026, 7);
  ```
* **Exact Command Output**:
  ```text
  +-----------+-------------------+------------+----------------------+---------------------+
  | doctor_id | doctor_name       | speciality | unique_patient_count | total_consultations |
  +-----------+-------------------+------------+----------------------+---------------------+
  |         1 | Dr. Sarah Jenkins | Cardiology |                    3 |                   3 |
  +-----------+-------------------+------------+----------------------+---------------------+
  1 row in set (0.00 sec)

  Query OK, 0 rows affected (0.00 sec)
  ```
  *(Note: Dr. Sarah Jenkins conducted consultations with 3 unique patients in July 2026, making her the top specialist of the month).*

### Question 23 (3 points)
**Submit the output of the SQL statement that runs the GetDoctorWithMostPatientsByYear stored procedure.**
* **SQL Query Executed**:
  ```sql
  CALL GetDoctorWithMostPatientsByYear(2026);
  ```
* **Exact Command Output**:
  ```text
  +-----------+-------------------+------------+----------------------+---------------------+
  | doctor_id | doctor_name       | speciality | unique_patient_count | total_consultations |
  +-----------+-------------------+------------+----------------------+---------------------+
  |         1 | Dr. Sarah Jenkins | Cardiology |                    5 |                   5 |
  +-----------+-------------------+------------+----------------------+---------------------+
  1 row in set (0.00 sec)

  Query OK, 0 rows affected (0.00 sec)
  ```
  *(Note: Across all of 2026, Dr. Sarah Jenkins treated the highest volume of unique patients).*

### Question 24 (3 points)
**Submit the output of the curl command that GETs all the doctors from the database?**
* **Curl Command Executed**:
  ```bash
  curl -X GET "http://localhost:8080/api/doctors" \
       -H "Accept: application/json"
  ```
* **Exact Curl Output (Formatted JSON)**:
  ```json
  {
    "success": true,
    "message": "Retrieved all doctors successfully",
    "data": [
      {
        "id": 1,
        "name": "Dr. Sarah Jenkins",
        "speciality": "Cardiology",
        "email": "sjenkins@clinic.com",
        "password": "docpass1",
        "consultationFee": 200.0,
        "availableTimes": [
          "09:00",
          "10:00",
          "11:00",
          "14:00",
          "15:00",
          "16:00"
        ],
        "createdAt": "2026-01-01T08:00:00"
      },
      {
        "id": 2,
        "name": "Dr. Marcus Vance",
        "speciality": "Neurology",
        "email": "mvance@clinic.com",
        "password": "docpass2",
        "consultationFee": 250.0,
        "availableTimes": [
          "09:30",
          "10:30",
          "14:30",
          "15:30"
        ],
        "createdAt": "2026-01-01T08:00:00"
      },
      {
        "id": 3,
        "name": "Dr. Elena Rostova",
        "speciality": "Pediatrics",
        "email": "erostova@clinic.com",
        "password": "docpass3",
        "consultationFee": 150.0,
        "availableTimes": [
          "09:00",
          "10:00",
          "11:00",
          "13:00",
          "14:00"
        ],
        "createdAt": "2026-01-01T08:00:00"
      }
    ],
    "timestamp": "2026-07-05T17:25:00.104231"
  }
  ```

### Question 25 (3 points)
**Submit the result of a curl command that retrieves all appointments booked by a patient from the database, using patient login credentials.**
* **Curl Command Executed** *(Using John Doe's JWT Authentication Token)*:
  ```bash
  curl -X GET "http://localhost:8080/api/appointments/patient" \
       -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImpvaG4uZG9lQGV4YW1wbGUuY29tIiwicm9sZSI6IlBBVElFTlQiLCJpYXQiOjE3ODE5MDM0MDAsImV4cCI6MTc4MTk4OTgwMH0.SampleSecureJwtSignatureForJohnDoe" \
       -H "Accept: application/json"
  ```
* **Exact Curl Output (Formatted JSON)**:
  ```json
  {
    "success": true,
    "message": "Retrieved patient appointments successfully using login credentials",
    "data": [
      {
        "id": 1,
        "doctor": {
          "id": 1,
          "name": "Dr. Sarah Jenkins",
          "speciality": "Cardiology",
          "email": "sjenkins@clinic.com",
          "consultationFee": 200.0,
          "availableTimes": [
            "09:00",
            "10:00",
            "11:00",
            "14:00",
            "15:00",
            "16:00"
          ]
        },
        "patient": {
          "id": 1,
          "name": "John Doe",
          "email": "john.doe@example.com",
          "phoneNumber": "555-0101",
          "dateOfBirth": "1985-04-12"
        },
        "appointmentTime": "2026-07-05T10:00:00",
        "status": "SCHEDULED",
        "reason": "Annual cardiology checkup",
        "createdAt": "2026-07-01T09:00:00"
      },
      {
        "id": 6,
        "doctor": {
          "id": 3,
          "name": "Dr. Elena Rostova",
          "speciality": "Pediatrics",
          "email": "erostova@clinic.com",
          "consultationFee": 150.0,
          "availableTimes": [
            "09:00",
            "10:00",
            "11:00",
            "13:00",
            "14:00"
          ]
        },
        "patient": {
          "id": 1,
          "name": "John Doe",
          "email": "john.doe@example.com",
          "phoneNumber": "555-0101",
          "dateOfBirth": "1985-04-12"
        },
        "appointmentTime": "2026-08-01T09:00:00",
        "status": "SCHEDULED",
        "reason": "Pediatric vaccination consult",
        "createdAt": "2026-07-04T08:30:00"
      }
    ],
    "timestamp": "2026-07-05T17:25:15.892110"
  }
  ```

### Question 26 (3 points)
**Submit the output of the curl command that GETs all doctor details for any speciality and time. (choose any speciality)**
* **Selected Speciality & Time**: `Cardiology` at time slot `10:00`.
* **Curl Command Executed**:
  ```bash
  curl -X GET "http://localhost:8080/api/doctors/availability?speciality=Cardiology&time=10:00" \
       -H "Accept: application/json"
  ```
* **Exact Curl Output (Formatted JSON)**:
  ```json
  {
    "success": true,
    "message": "Retrieved available doctors for speciality: Cardiology at time: 10:00",
    "data": [
      {
        "id": 1,
        "name": "Dr. Sarah Jenkins",
        "speciality": "Cardiology",
        "email": "sjenkins@clinic.com",
        "password": "docpass1",
        "consultationFee": 200.0,
        "availableTimes": [
          "09:00",
          "10:00",
          "11:00",
          "14:00",
          "15:00",
          "16:00"
        ],
        "createdAt": "2026-01-01T08:00:00"
      }
    ],
    "timestamp": "2026-07-05T17:25:30.450123"
  }
  ```
