package com.busbooking.payment_service.repository;

import com.busbooking.payment_service.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransactionEntity, Long> {

    List<PaymentTransactionEntity> findByPaymentId(Long paymentId);

    List<PaymentTransactionEntity> findByGatewayRef(String gatewayRef);

    Optional<PaymentTransactionEntity>
    findTopByPaymentIdOrderByCreatedDateDesc(Long paymentId);
}