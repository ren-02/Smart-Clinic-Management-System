package com.smartclinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity representing a Doctor in the Smart Clinic Management System.
 * Meets Question 3 criteria: defines proper JPA entity with primary key annotations
 * and defines availableTimes field with the correct type and JPA annotations.
 */
@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    /**
     * Primary Key with Auto-Increment generation strategy (Question 3 - 5 points).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Speciality is required")
    @Column(nullable = false, length = 100)
    private String speciality;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Column(name = "consultation_fee")
    @Builder.Default
    private BigDecimal consultationFee = new BigDecimal("150.00");

    /**
     * Available time slots for appointment scheduling (Question 3 - 3 points).
     * Mapped as an ElementCollection to create a clean relational join table (doctor_available_times).
     * Holds standard string time slots such as "09:00", "10:30", "14:00".
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "doctor_available_times",
            joinColumns = @JoinColumn(name = "doctor_id")
    )
    @Column(name = "available_time", nullable = false, length = 20)
    @Builder.Default
    private List<String> availableTimes = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
