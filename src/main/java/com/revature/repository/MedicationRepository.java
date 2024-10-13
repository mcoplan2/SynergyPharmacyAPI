package com.revature.repository;


import com.revature.model.Medication;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> getAllByType(Type type);
    List<Medication> getAllByStatus(Status status);
    Optional<Medication> findByName(String name);
    List<Medication> findByNameStartingWith(String letter);
    List<Medication> findByNameContainingIgnoreCase(String query);
}
