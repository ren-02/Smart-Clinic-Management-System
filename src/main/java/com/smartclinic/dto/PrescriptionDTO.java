package com.smartclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long appointmentId;

    @NotBlank(message = "Medications list cannot be empty")
    private String medications;

    @NotBlank(message = "Dosage information cannot be empty")
    private String dosage;

    private String instructions;
}
