package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.AppointmentDTO;
import com.smartclinic.model.Appointment;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenService tokenService;

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Appointment>> bookAppointment(
            @Valid @RequestBody AppointmentDTO dto,
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid JWT Bearer token required to book appointment"));
        }

        try {
            Appointment saved = appointmentService.bookAppointment(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Appointment booked successfully", saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve appointments for a doctor on a specific date (satisfies Question 6 criteria).
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<Appointment>>> getDoctorAppointmentsOnDate(
            @PathVariable Long doctorId,
            @RequestParam("date") String dateStr,
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid JWT Bearer token required"));
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, date);
        return ResponseEntity.ok(ApiResponse.ok("Retrieved doctor appointments for date: " + dateStr, appointments));
    }

    /**
     * Retrieves all appointments booked by a patient using their JWT login credentials (satisfies Question 25).
     */
    @GetMapping("/patient")
    public ResponseEntity<ApiResponse<List<Appointment>>> getPatientAppointmentsByToken(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Unauthorized: Valid patient JWT login credentials required"));
        }

        String email = tokenService.extractEmail(token);
        List<Appointment> appointments = appointmentService.getAppointmentsForPatientByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok("Retrieved patient appointments successfully using login credentials", appointments));
    }
}
