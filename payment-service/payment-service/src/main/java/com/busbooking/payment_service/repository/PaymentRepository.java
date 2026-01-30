package com.busbooking.payment_service.repository;

import com.busbooking.payment_service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByPnrId(Long pnrId);

    // ✅ FIXED
    List<PaymentEntity> findByStatus(String status);
}