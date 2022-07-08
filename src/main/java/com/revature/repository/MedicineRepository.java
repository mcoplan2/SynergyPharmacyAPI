package com.revature.repository;


import com.revature.model.Medicine;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    List<Type> getAllByType(Type type);
    List<Status> getAllByStatus(Status status);
}
