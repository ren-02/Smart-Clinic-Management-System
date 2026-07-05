package com.smartclinic.service;

import com.smartclinic.dto.LoginResponse;
import com.smartclinic.model.Patient;
import com.smartclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final TokenService tokenService;

    @Transactional
    public Patient registerPatient(Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered: " + patient.getEmail());
        }
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(String email, String password) {
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        if (patientOpt.isEmpty() || !patientOpt.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid login credentials for patient");
        }
        Patient patient = patientOpt.get();
        String token = tokenService.generateToken(patient.getEmail(), "PATIENT");
        return LoginResponse.success(
                token,
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                "PATIENT",
                "Patient authentication successful"
        );
    }

    @Transactional(readOnly = true)
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with email: " + email));
    }
}
