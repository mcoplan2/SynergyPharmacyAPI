package com.revature.repository;

import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> getAllByPayStatus(PayStatus payStatus);

    List<Payment> getAllByUserId(User userId);

    Optional<Payment> findByUserId_UserIdAndMedicineId_MedIdAndReqId_DosageCountAndReqId_DosageFreq(Integer userId, Integer medId, Integer dosageCount, Integer dosageFreq);
}
