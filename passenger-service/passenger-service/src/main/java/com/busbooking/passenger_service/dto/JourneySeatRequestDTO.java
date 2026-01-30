package com.busbooking.passenger_service.dto;

import java.util.List;

public class JourneySeatRequestDTO {

    private Long saId;
    private List<SeatPassengerDTO> seats;

    public Long getSaId() {
        return saId;
    }

    public void setSaId(Long saId) {
        this.saId = saId;
    }

    public List<SeatPassengerDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatPassengerDTO> seats) {
        this.seats = seats;
    }
}