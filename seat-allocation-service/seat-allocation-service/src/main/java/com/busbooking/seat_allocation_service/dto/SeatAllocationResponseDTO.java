package com.busbooking.seat_allocation_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SeatAllocationResponseDTO {

    private Long saId;
    private Long scheduleId;
    private LocalDate dateOfJourney;

    private List<String> lowerDeckLayout;
    private List<String> upperDeckLayout;

    private List<String> lowerDeckLayoutSeats;
    private List<String> upperDeckLayoutSeats;

    private String status;

    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private LocalDateTime lastUpdatedDate;
    private String lastUpdatedBy;

    private List<PassengerSeatDTO> passengers;

    public SeatAllocationResponseDTO() {
    }

    public Long getSaId() {
        return saId;
    }

    public void setSaId(Long saId) {
        this.saId = saId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(LocalDate dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public List<String> getLowerDeckLayout() {
        return lowerDeckLayout;
    }

    public void setLowerDeckLayout(List<String> lowerDeckLayout) {
        this.lowerDeckLayout = lowerDeckLayout;
    }

    public List<String> getUpperDeckLayout() {
        return upperDeckLayout;
    }

    public void setUpperDeckLayout(List<String> upperDeckLayout) {
        this.upperDeckLayout = upperDeckLayout;
    }

    public List<String> getLowerDeckLayoutSeats() {
        return lowerDeckLayoutSeats;
    }

    public void setLowerDeckLayoutSeats(List<String> lowerDeckLayoutSeats) {
        this.lowerDeckLayoutSeats = lowerDeckLayoutSeats;
    }

    public List<String> getUpperDeckLayoutSeats() {
        return upperDeckLayoutSeats;
    }

    public void setUpperDeckLayoutSeats(List<String> upperDeckLayoutSeats) {
        this.upperDeckLayoutSeats = upperDeckLayoutSeats;
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

    public List<PassengerSeatDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerSeatDTO> passengers) {
        this.passengers = passengers;
    }
}