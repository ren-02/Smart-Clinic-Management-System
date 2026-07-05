package com.smartclinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA Entity representing a medical Prescription issued by a Doctor to a Patient.
 */
@Entity
@Table(name = "prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor association is required")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient association is required")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @NotBlank(message = "Medications list cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String medications;

    @NotBlank(message = "Dosage instructions cannot be blank")
    @Column(nullable = false, length = 100)
    private String dosage;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
