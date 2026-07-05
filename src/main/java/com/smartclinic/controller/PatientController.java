package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.LoginRequest;
import com.smartclinic.dto.LoginResponse;
import com.smartclinic.model.Patient;
import com.smartclinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Patient>> register(@Valid @RequestBody Patient patient) {
        try {
            Patient saved = patientService.registerPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Patient registered successfully", saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = patientService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(ApiResponse.ok("Patient login successful", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Patient>>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponse.ok("Retrieved all patients", patients));
    }
}
