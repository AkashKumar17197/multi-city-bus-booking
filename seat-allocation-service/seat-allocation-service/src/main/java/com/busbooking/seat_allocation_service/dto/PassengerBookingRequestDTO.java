package com.busbooking.seat_allocation_service.dto;

import java.util.List;

public class PassengerBookingRequestDTO {

    private String phoneNumber;
    private String emailId;
    private String createdBy;
    private List<PassengerInfoDTO> passengers;
    private List<JourneySeatRequestDTO> journeys;

    // Getters & Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<PassengerInfoDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerInfoDTO> passengers) {
        this.passengers = passengers;
    }

    public List<JourneySeatRequestDTO> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneySeatRequestDTO> journeys) {
        this.journeys = journeys;
    }
}
