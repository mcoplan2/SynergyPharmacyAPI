package com.revature.repository;

import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> getAllByPayStatus(PayStatus payStatus);

    List<Payment> getAllByUserId(User userId);
}
