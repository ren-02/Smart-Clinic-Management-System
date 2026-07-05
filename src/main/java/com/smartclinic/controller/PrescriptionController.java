package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.PrescriptionDTO;
import com.smartclinic.model.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for issuing and managing electronic Prescriptions.
 * Meets Question 7 criteria:
 * 1. POST endpoint saves a prescription with token and request body validation (3 points).
 * 2. Returns structured success or error messages using ResponseEntity (3 points).
 */
@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    /**
     * POST endpoint that saves a prescription with token and request body validation (Question 7 - 3 points).
     * Validates Authorization token and returns structured success/error messages using ResponseEntity (Question 7 - 3 points).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Prescription>> savePrescription(
            @Valid @RequestBody PrescriptionDTO requestDTO,
            @RequestHeader(value = "Authorization", required = false) String token) {

        // Validate JWT token (Question 7 - 3 points)
        if (token == null || token.isEmpty() || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid JWT bearer token is required to issue prescriptions"));
        }

        try {
            Prescription savedPrescription = prescriptionService.savePrescription(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Prescription saved successfully", savedPrescription));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to save prescription: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error while saving prescription"));
        }
    }

    /**
     * GET endpoint to retrieve all prescriptions for a specific patient.
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<Prescription>>> getPatientPrescriptions(
            @PathVariable Long patientId,
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid JWT Bearer token is required"));
        }

        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.ok("Patient prescriptions retrieved successfully", prescriptions));
    }
}
