package com.busbooking.seat_allocation_service.dto;

import java.util.List;

public class SeatBulkBookingRequestDTO {
    private Long saId;
    private List<PassengerBookingDTO> passengers;

    // getters & setters
    public Long getSaId() { return saId; }
    public void setSaId(Long saId) { this.saId = saId; }

    public List<PassengerBookingDTO> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerBookingDTO> passengers) { this.passengers = passengers; }
}