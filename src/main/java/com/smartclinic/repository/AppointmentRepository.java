package com.smartclinic.repository;

import com.smartclinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientEmail(String email);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentTime >= :start AND a.appointmentTime <= :end")
    List<Appointment> findDoctorAppointmentsOnDate(@Param("doctorId") Long doctorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
