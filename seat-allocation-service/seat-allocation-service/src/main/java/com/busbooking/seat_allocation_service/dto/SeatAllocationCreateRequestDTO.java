package com.busbooking.seat_allocation_service.dto;

import java.time.LocalDate;
import java.util.List;

public class SeatAllocationCreateRequestDTO {

    private Long scheduleId;
    private LocalDate dateOfJourney;

    private List<String> lowerDeckLayout;
    private List<String> upperDeckLayout;

    private List<String> lowerDeckLayoutSeats;
    private List<String> upperDeckLayoutSeats;

    private List<String> lowerDeckLayoutSeatsOccupied;
    private List<String> upperDeckLayoutSeatsOccupied;

    public SeatAllocationCreateRequestDTO() {
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

    public List<String> getLowerDeckLayoutSeatsOccupied() {
        return lowerDeckLayoutSeatsOccupied;
    }

    public void setLowerDeckLayoutSeatsOccupied(List<String> lowerDeckLayoutSeatsOccupied) {
        this.lowerDeckLayoutSeatsOccupied = lowerDeckLayoutSeatsOccupied;
    }

    public List<String> getUpperDeckLayoutSeatsOccupied() {
        return upperDeckLayoutSeatsOccupied;
    }

    public void setUpperDeckLayoutSeatsOccupied(List<String> upperDeckLayoutSeatsOccupied) {
        this.upperDeckLayoutSeatsOccupied = upperDeckLayoutSeatsOccupied;
    }
}