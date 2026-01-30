package com.busbooking.payment_service.dto;

import java.math.BigDecimal;

public class PaymentTransactionRequestDTO {

    private Long paymentId;
    private String gatewayName; // Razorpay / Paytm / PhonePe
    private String gatewayRef;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getGatewayRef() {
        return gatewayRef;
    }

    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }
}