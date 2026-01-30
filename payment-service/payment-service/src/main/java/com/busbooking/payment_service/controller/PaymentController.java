package com.busbooking.payment_service.controller;

import com.busbooking.payment_service.dto.PaymentInitiateRequestDTO;
import com.busbooking.payment_service.dto.PaymentResponseDTO;
import com.busbooking.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ---------------- INITIATE PAYMENT ----------------
    @PostMapping("/initiate")
    public PaymentResponseDTO initiatePayment(
            @RequestBody PaymentInitiateRequestDTO request) {

        return paymentService.initiatePayment(request);
    }

    // ---------------- FETCH PAYMENT BY PNR ----------------
    @GetMapping("/pnr/{pnrId}")
    public PaymentResponseDTO getPaymentByPnr(
            @PathVariable Long pnrId) {

        return paymentService.getPaymentByPnrId(pnrId);
    }

    // ---------------- UPDATE PAYMENT STATUS ----------------
    @PutMapping("/{paymentId}/status")
    public PaymentResponseDTO updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status,
            @RequestParam String updatedBy) {

        return paymentService.updatePaymentStatus(paymentId, status, updatedBy);
    }
}