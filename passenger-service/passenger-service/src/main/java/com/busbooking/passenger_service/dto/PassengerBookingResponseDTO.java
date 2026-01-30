package com.busbooking.passenger_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PassengerBookingResponseDTO {

    private Long pnrId;
    private String pnrNumber;
    private String phoneNumber;
    private String emailId;
    private String status;

    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private LocalDateTime lastUpdatedDate;
    private String lastUpdatedBy;

    private List<PassengerResponseDTO> passengers;
    private List<JourneyResponseDTO> journeys;

    // Getters & Setters (same pattern)

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public List<PassengerResponseDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerResponseDTO> passengers) {
        this.passengers = passengers;
    }

    public List<JourneyResponseDTO> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyResponseDTO> journeys) {
        this.journeys = journeys;
    }
}