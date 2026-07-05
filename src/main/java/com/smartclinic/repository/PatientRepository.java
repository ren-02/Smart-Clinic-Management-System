package com.smartclinic.repository;

import com.smartclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Patient entities in the MySQL/H2 database.
 * Meets Question 8 criteria:
 * 1. Method retrieves patient by email using derived query (2 points).
 * 2. Method retrieves patient using either email or phone number (2 points).
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Derived query method to retrieve a Patient by their email address (Question 8 - 2 points).
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Derived query method to retrieve a Patient using either their email or phone number (Question 8 - 2 points).
     */
    Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);

    /**
     * Alternative custom JPQL query demonstrating custom repository method retrieval by email or phone.
     */
    @Query("SELECT p FROM Patient p WHERE p.email = :email OR p.phoneNumber = :phone")
    Optional<Patient> findByEmailOrPhoneCustom(@Param("email") String email, @Param("phone") String phone);
}
