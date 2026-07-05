# Smart Clinic Management System - Project Issues & User Stories

This document compiles the defined user stories and agile backlog issues for the Smart Clinic Management System. All user stories are structured using standard role-based Agile formatting (`As a [Role], I want [Feature] so that [Benefit]`) and cover all major application functionalities across Doctors, Patients, and Administrators.

---

## 🏥 Epic 1: Patient Management & Appointment Booking

### Issue #1: Patient Registration and Profile Management
* **Role**: Patient
* **User Story**: As a **Patient**, I want to register an account using my email or phone number and manage my profile details so that I can securely access the clinic services and book medical appointments.
* **Acceptance Criteria**:
  - [x] Patient can register with Name, Email, Phone Number, Password, and Date of Birth.
  - [x] System validates unique email and phone number using derived repository methods (`findByEmail` and `findByEmailOrPhoneNumber`).
  - [x] Password is encrypted before persisting to the relational MySQL/H2 database.
  - [x] Patient can log in and receive a secure JWT authentication token.
* **Labels**: `role:patient`, `feature:auth`, `priority:high`

### Issue #2: Search Doctors and Check Availability
* **Role**: Patient
* **User Story**: As a **Patient**, I want to search for doctors by specialization and view their available time slots on specific dates so that I can find the right specialist at a convenient time.
* **Acceptance Criteria**:
  - [x] Patients can query `/api/doctors/availability` using dynamic parameters (`speciality`, `date`, `time`).
  - [x] System returns a structured response containing doctor profiles and lists of available appointment time slots.
* **Labels**: `role:patient`, `feature:appointment`, `priority:high`

### Issue #3: Book and View Appointments
* **Role**: Patient
* **User Story**: As a **Patient**, I want to book an appointment with a selected doctor for an available time slot and view my booking history so that I can manage my scheduled clinic consultations.
* **Acceptance Criteria**:
  - [x] Patient can book an appointment by selecting a doctor and specifying an valid `LocalDateTime` (`appointmentTime`).
  - [x] System validates that `appointmentTime` is in the present or future (`@FutureOrPresent`).
  - [x] Patient can retrieve all their booked appointments using their JWT login credentials via curl/REST API.
* **Labels**: `role:patient`, `feature:appointment`, `priority:high`

---

## 🩺 Epic 2: Doctor Schedule & Clinical Workflow

### Issue #4: Doctor Authentication & Dashboard
* **Role**: Doctor
* **User Story**: As a **Doctor**, I want to log in securely with my credentials so that I can access my personalized medical dashboard and view my consultation schedule.
* **Acceptance Criteria**:
  - [x] Doctor credentials (email and password) are validated via `DoctorService.login()`.
  - [x] System returns a structured `ResponseEntity` containing a JWT bearer token and doctor profile information.
* **Labels**: `role:doctor`, `feature:auth`, `priority:high`

### Issue #5: Manage Daily Schedule and Availability
* **Role**: Doctor
* **User Story**: As a **Doctor**, I want to set and view my available time slots for any given date so that patients can only book consultations when I am on duty.
* **Acceptance Criteria**:
  - [x] Doctor entity defines `availableTimes` mapped as a JPA `@ElementCollection`.
  - [x] `DoctorService.getAvailableTimeSlots()` retrieves time slots dynamically for a given date.
* **Labels**: `role:doctor`, `feature:schedule`, `priority:high`

### Issue #6: View Daily Appointments
* **Role**: Doctor
* **User Story**: As a **Doctor**, I want to retrieve a list of all appointments scheduled with me on a specific date so that I can prepare for patient consultations.
* **Acceptance Criteria**:
  - [x] `AppointmentService.getAppointmentsForDoctorOnDate()` filters and returns all patient bookings for the specified doctor and date.
* **Labels**: `role:doctor`, `feature:appointment`, `priority:medium`

### Issue #7: Create and Issue Electronic Prescriptions
* **Role**: Doctor
* **User Story**: As a **Doctor**, I want to issue and save medical prescriptions for patients after their consultation so that they receive official records of medications and dosage instructions.
* **Acceptance Criteria**:
  - [x] Doctor submits a POST request to `/api/prescriptions` with valid token and request body.
  - [x] System validates the input, associates the prescription with the doctor, patient, and appointment, and returns a structured success message using `ResponseEntity`.
* **Labels**: `role:doctor`, `feature:clinical`, `priority:high`

---

## ⚙️ Epic 3: Clinic Administration & System Reporting

### Issue #8: System Overview and Doctor Management
* **Role**: Admin
* **User Story**: As an **Admin**, I want to onboard new doctors, assign their specializations, and view all registered healthcare professionals so that the clinic maintains an up-to-date roster of specialists.
* **Acceptance Criteria**:
  - [x] Admin can retrieve all doctors from the database via REST endpoints (`GET /api/doctors`).
  - [x] Admin can manage doctor profiles, specialities, and schedules.
* **Labels**: `role:admin`, `feature:management`, `priority:high`

### Issue #9: Generate Daily Appointment Reports
* **Role**: Admin
* **User Story**: As an **Admin**, I want to run database stored procedures to generate daily appointment reports grouped by doctors so that clinic operations can track daily patient flow and physician workloads.
* **Acceptance Criteria**:
  - [x] System implements the stored procedure `GetDailyAppointmentReportByDoctor`.
  - [x] Admin can execute the procedure to get exact appointment counts and details per doctor for any given day.
* **Labels**: `role:admin`, `feature:reporting`, `priority:high`

### Issue #10: Monthly and Yearly Doctor Performance Analytics
* **Role**: Admin
* **User Story**: As an **Admin**, I want to analyze patient volume by running monthly and yearly stored procedures so that I can identify the clinic's most sought-after doctors and allocate resources effectively.
* **Acceptance Criteria**:
  - [x] System implements stored procedures `GetDoctorWithMostPatientsByMonth` and `GetDoctorWithMostPatientsByYear`.
  - [x] SQL queries return the top-performing doctors based on unique patient consultations across monthly and yearly timeframes.
* **Labels**: `role:admin`, `feature:analytics`, `priority:medium`

---

## 🔗 Repository Reference Links
* **Repository Root**: `https://github.com/vince/smart-clinic-management-system`
* **Issues Document**: [issues.md](file:///C:/Users/vince/OneDrive/Documents/javaProject/issues.md)
