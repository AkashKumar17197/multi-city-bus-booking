package com.busbooking.passenger_service.dto;

import java.util.List;

public class JourneyResponseDTO {

    private Long saId;
    private List<SeatJourneyResponseDTO> passengers;

    // Getters & Setters

    public Long getSaId() {
        return saId;
    }

    public void setSaId(Long saId) {
        this.saId = saId;
    }

    public List<SeatJourneyResponseDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<SeatJourneyResponseDTO> passengers) {
        this.passengers = passengers;
    }

}