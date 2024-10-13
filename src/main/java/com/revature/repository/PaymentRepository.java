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

    List<Payment> getAllByUser_UserId(Integer id);

    List<Payment> getAllByUser_UserIdAndPayStatus(Integer id, PayStatus payStatus);

    Optional<Payment> findByUser_UserIdAndMed_MedIdAndReq_DosageCountAndReq_DosageFreq(Integer userId, Integer medId, Integer dosageCount, Integer dosageFreq);
}
