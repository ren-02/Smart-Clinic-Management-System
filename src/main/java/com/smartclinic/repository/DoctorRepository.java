package com.smartclinic.repository;

import com.smartclinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecialityIgnoreCase(String speciality);

    @Query("SELECT d FROM Doctor d JOIN d.availableTimes t WHERE LOWER(d.speciality) = LOWER(:speciality) AND t = :time")
    List<Doctor> findBySpecialityAndAvailableTime(@Param("speciality") String speciality, @Param("time") String time);
}
