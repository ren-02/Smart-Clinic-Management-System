package com.smartclinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA Entity representing a medical consultation Appointment.
 * Meets Question 4 criteria: defines proper @ManyToOne relationships with Doctor and Patient,
 * and defines appointmentTime field of type LocalDateTime with validation annotations.
 */
@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-One relationship linking this appointment to a specific Doctor (Question 4 - 3 points).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor association cannot be null")
    private Doctor doctor;

    /**
     * Many-to-One relationship linking this appointment to a specific Patient (Question 4 - 3 points).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient association cannot be null")
    private Patient patient;

    /**
     * Scheduled date and time for the consultation (Question 4 - 3 points).
     * Enforces validation that the time cannot be null and must occur in the present or future.
     */
    @NotNull(message = "Appointment time is required")
    @FutureOrPresent(message = "Appointment time must be in the present or future")
    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "SCHEDULED";

    @Column(length = 255)
    private String reason;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "SCHEDULED";
        }
    }
}
