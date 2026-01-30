package com.busbooking.passenger_service.dto;

public class PassengerBookingDeleteResponseDTO {

    private Long pnrId;
    private String pnrNumber;
    private String status;

    // Getters & Setters

    public Long getPnrId() {
        return pnrId;
    }

    public void setPnrId(Long pnrId) {
        this.pnrId = pnrId;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}