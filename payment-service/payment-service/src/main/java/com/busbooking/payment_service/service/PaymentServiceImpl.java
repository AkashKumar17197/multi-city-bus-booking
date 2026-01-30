package com.busbooking.payment_service.service;

import com.busbooking.payment_service.dto.PaymentInitiateRequestDTO;
import com.busbooking.payment_service.dto.PaymentResponseDTO;
import com.busbooking.payment_service.entity.PaymentEntity;
import com.busbooking.payment_service.entity.PaymentTransactionEntity;
import com.busbooking.payment_service.repository.PaymentRepository;
import com.busbooking.payment_service.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository transactionRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentTransactionRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
    }

    // ---------------- INITIATE PAYMENT ----------------
    @Override
    public PaymentResponseDTO initiatePayment(PaymentInitiateRequestDTO request) {

        // 1️⃣ Create Payment
        PaymentEntity payment = new PaymentEntity();
        payment.setPnrId(request.getPnrId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("INITIATED");
        payment.setCreatedBy(request.getCreatedBy());
        payment.setCreatedDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        // 2️⃣ Create initial transaction (audit / gateway placeholder)
        PaymentTransactionEntity tx = new PaymentTransactionEntity();
        tx.setPaymentId(payment.getPaymentId());
        tx.setStatus("PENDING");
        tx.setCreatedDate(LocalDateTime.now());

        transactionRepository.save(tx);

        return mapToResponse(payment);
    }

    // ---------------- FETCH PAYMENT ----------------
    @Override
    public PaymentResponseDTO getPaymentByPnrId(Long pnrId) {

        PaymentEntity payment = paymentRepository
                .findByPnrId(pnrId)
                .orElseThrow(() -> new RuntimeException("Payment not found for PNR: " + pnrId));

        return mapToResponse(payment);
    }

    // ---------------- UPDATE PAYMENT STATUS ----------------
    @Override
    public PaymentResponseDTO updatePaymentStatus(Long paymentId,
                                                  String status,
                                                  String updatedBy) {

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        payment.setStatus(status);
        payment.setUpdatedBy(updatedBy);
        payment.setUpdatedDate(LocalDateTime.now());

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    // ---------------- ENTITY → DTO MAPPER ----------------
    private PaymentResponseDTO mapToResponse(PaymentEntity payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setPnrId(payment.getPnrId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getStatus());
        dto.setCreatedDate(payment.getCreatedDate());

        return dto;
    }
}