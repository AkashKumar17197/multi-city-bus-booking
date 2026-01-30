package com.busbooking.payment_service.service;

import com.busbooking.payment_service.dto.PaymentInitiateRequestDTO;
import com.busbooking.payment_service.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO initiatePayment(PaymentInitiateRequestDTO request);

    PaymentResponseDTO getPaymentByPnrId(Long pnrId);

    PaymentResponseDTO updatePaymentStatus(
            Long paymentId,
            String status,
            String updatedBy
    );
}