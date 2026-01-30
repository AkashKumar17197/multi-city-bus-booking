package com.busbooking.passenger_service.dto;

public class SeatJourneyResponseDTO {

    private Long pjId;
    private Long passId;
    private String seatNo;

    // Getters & Setters

    public Long getPjId() {
        return pjId;
    }

    public void setPjId(Long pjId) {
        this.pjId = pjId;
    }

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