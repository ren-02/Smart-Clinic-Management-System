package com.smartclinic.service;

import com.smartclinic.dto.PrescriptionDTO;
import com.smartclinic.model.Appointment;
import com.smartclinic.model.Doctor;
import com.smartclinic.model.Patient;
import com.smartclinic.model.Prescription;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import com.smartclinic.repository.PatientRepository;
import com.smartclinic.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Prescription savePrescription(PrescriptionDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + dto.getDoctorId()));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + dto.getPatientId()));

        Appointment appointment = null;
        if (dto.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(dto.getAppointmentId()).orElse(null);
        }

        Prescription prescription = Prescription.builder()
                .doctor(doctor)
                .patient(patient)
                .appointment(appointment)
                .medications(dto.getMedications())
                .dosage(dto.getDosage())
                .instructions(dto.getInstructions())
                .build();

        return prescriptionRepository.save(prescription);
    }

    @Transactional(readOnly = true)
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }
}
