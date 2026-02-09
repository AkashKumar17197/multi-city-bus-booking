package com.busbooking.seat_allocation_service.dto;

public class SeatPassengerDTO {

    private Long passId;    // optional, can be null before saving passenger-service
    private String seatNo;

    // Getters & Setters
    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
}