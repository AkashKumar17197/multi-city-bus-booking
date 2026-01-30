package com.busbooking.passenger_service.dto;

import java.util.List;

public class PassengerBookingUpdateRequestDTO {

    private String phoneNumber;
    private String emailId;
    private String updatedBy;
    private List<PassengerInfoUpdateDTO> passengers;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<PassengerInfoUpdateDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerInfoUpdateDTO> passengers) {
        this.passengers = passengers;
    }

    public List<JourneySeatRequestDTO> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneySeatRequestDTO> journeys) {
        this.journeys = journeys;
    }
}