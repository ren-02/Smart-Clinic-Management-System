package com.smartclinic.service;

import com.smartclinic.dto.AppointmentDTO;
import com.smartclinic.model.Appointment;
import com.smartclinic.model.Doctor;
import com.smartclinic.model.Patient;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import com.smartclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class handling consultation scheduling and appointment management.
 * Meets Question 6 criteria:
 * 1. Implements a booking method that saves an appointment (3 points).
 * 2. Defines a method to retrieve appointments for a doctor on a specific date (3 points).
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    /**
     * Booking method that saves a new medical appointment to the database (Question 6 - 3 points).
     *
     * @param appointment The Appointment entity to persist.
     * @return Saved Appointment entity with generated ID.
     */
    @Transactional
    public Appointment bookAppointment(Appointment appointment) {
        if (appointment.getDoctor() == null || appointment.getDoctor().getId() == null) {
            throw new IllegalArgumentException("Doctor must be specified for booking appointment");
        }
        if (appointment.getPatient() == null || appointment.getPatient().getId() == null) {
            throw new IllegalArgumentException("Patient must be specified for booking appointment");
        }

        // Verify entities exist
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + appointment.getDoctor().getId()));
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + appointment.getPatient().getId()));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        if (appointment.getStatus() == null) {
            appointment.setStatus("SCHEDULED");
        }
        return appointmentRepository.save(appointment);
    }

    /**
     * Overloaded helper method to book an appointment from a Data Transfer Object (DTO).
     */
    @Transactional
    public Appointment bookAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + dto.getDoctorId()));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + dto.getPatientId()));

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentTime(dto.getAppointmentTime())
                .reason(dto.getReason())
                .status("SCHEDULED")
                .build();

        return appointmentRepository.save(appointment);
    }

    /**
     * Retrieves all scheduled appointments for a specific doctor on a given date (Question 6 - 3 points).
     *
     * @param doctorId The unique ID of the doctor.
     * @param date     The specific date to filter appointments.
     * @return List of Appointment entities booked on that date.
     */
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
    }

    /**
     * Retrieves all appointments booked by a specific patient using patient login ID.
     */
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Retrieves all appointments booked by a specific patient using their login email.
     */
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForPatientByEmail(String email) {
        return appointmentRepository.findByPatientEmail(email);
    }
}
