package com.busbooking.passenger_service.dto;

public class SeatPassengerDTO {

    private Long passId;
    private String seatNo;

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