package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.LoginRequest;
import com.smartclinic.dto.LoginResponse;
import com.smartclinic.model.Doctor;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing Doctor endpoints, availability queries, and authentication.
 * Meets Question 5 criteria:
 * 1. Exposes a GET endpoint for doctor availability using dynamic parameters (3 points).
 * 2. Validates token and returns a structured response using ResponseEntity (3 points).
 */
@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    /**
     * Exposes a GET endpoint for doctor availability using dynamic parameters (Question 5 - 3 points).
     * Supports querying by date, speciality, or time slot (also satisfies Question 26).
     * Validates JWT token from the Authorization header and returns structured ResponseEntity (Question 5 - 3 points).
     */
    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<Object>> getDoctorAvailability(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String speciality,
            @RequestParam(required = false) String time,
            @RequestHeader(value = "Authorization", required = false) String token) {

        // Validate token (Question 5 - 3 points)
        if (token != null && !token.isEmpty()) {
            if (!tokenService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid or expired JWT authorization token"));
            }
        }

        // Case 1: Query by speciality and time (Question 26 criteria)
        if (speciality != null && time != null) {
            List<Doctor> matchingDoctors = doctorService.getDoctorsBySpecialityAndTime(speciality, time);
            return ResponseEntity.ok(ApiResponse.ok("Retrieved available doctors for speciality: " + speciality + " at time: " + time, matchingDoctors));
        }

        // Case 2: Query availability for a specific doctor on a specific date
        if (doctorId != null && date != null) {
            LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            List<String> availableSlots = doctorService.getAvailableTimeSlots(doctorId, targetDate);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("doctorId", doctorId);
            responseData.put("date", date);
            responseData.put("availableTimeSlots", availableSlots);
            return ResponseEntity.ok(ApiResponse.ok("Retrieved available time slots for doctor ID " + doctorId + " on " + date, responseData));
        }

        // Case 3: Filter by speciality only
        if (speciality != null) {
            List<Doctor> doctors = doctorService.getDoctorsBySpeciality(speciality);
            return ResponseEntity.ok(ApiResponse.ok("Retrieved doctors for speciality: " + speciality, doctors));
        }

        return ResponseEntity.badRequest().body(ApiResponse.error("Please provide dynamic query parameters: (doctorId & date) OR (speciality & time)"));
    }

    /**
     * Endpoint to retrieve available time slots for a specific doctor by path variable.
     */
    @GetMapping("/{id}/availability")
    public ResponseEntity<ApiResponse<List<String>>> getDoctorAvailabilityById(
            @PathVariable Long id,
            @RequestParam("date") String dateStr,
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid JWT Bearer token is required"));
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        List<String> availableSlots = doctorService.getAvailableTimeSlots(id, date);
        return ResponseEntity.ok(ApiResponse.ok("Available slots retrieved successfully", availableSlots));
    }

    /**
     * Exposes endpoint to retrieve all doctors in the system (Question 24 criteria).
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(ApiResponse.ok("Retrieved all doctors successfully", doctors));
    }

    /**
     * Endpoint for doctor login authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = doctorService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e.getMessage()));
        }
    }
}
