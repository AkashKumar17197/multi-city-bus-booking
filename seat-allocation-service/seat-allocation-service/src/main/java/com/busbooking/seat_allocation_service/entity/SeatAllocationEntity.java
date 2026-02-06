package com.busbooking.seat_allocation_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seat_allocations_sa")
public class SeatAllocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sa_id")
    private Long saId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "date_of_journey", nullable = false)
    private LocalDate dateOfJourney;

    // -------- LOWER DECK --------
    @Column(name = "lower_deck_layout", columnDefinition = "TEXT", nullable = false)
    private String lowerDeckLayout;

    @Column(name = "lower_deck_layout_seats", columnDefinition = "TEXT", nullable = false)
    private String lowerDeckLayoutSeats;

    // -------- UPPER DECK --------
    @Column(name = "upper_deck_layout", columnDefinition = "TEXT")
    private String upperDeckLayout;

    @Column(name = "upper_deck_layout_seats", columnDefinition = "TEXT")
    private String upperDeckLayoutSeats;

    // -------- STATUS & AUDIT --------
    @Column(name = "status", nullable = false)
    private String status; // LIVE / BLOCKED / CANCELLED

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @OneToMany(
            mappedBy = "seatAllocation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SeatPassengerEntity> passengerSeats;

    // -------- GETTERS & SETTERS --------

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

    public String getLowerDeckLayout() {
        return lowerDeckLayout;
    }

    public void setLowerDeckLayout(String lowerDeckLayout) {
        this.lowerDeckLayout = lowerDeckLayout;
    }

    public String getLowerDeckLayoutSeats() {
        return lowerDeckLayoutSeats;
    }

    public void setLowerDeckLayoutSeats(String lowerDeckLayoutSeats) {
        this.lowerDeckLayoutSeats = lowerDeckLayoutSeats;
    }

    public String getUpperDeckLayout() {
        return upperDeckLayout;
    }

    public void setUpperDeckLayout(String upperDeckLayout) {
        this.upperDeckLayout = upperDeckLayout;
    }

    public String getUpperDeckLayoutSeats() {
        return upperDeckLayoutSeats;
    }

    public void setUpperDeckLayoutSeats(String upperDeckLayoutSeats) {
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

    public List<SeatPassengerEntity> getPassengerSeats() {
        return passengerSeats;
    }

    public void setPassengerSeats(List<SeatPassengerEntity> passengerSeats) {
        this.passengerSeats = passengerSeats;
    }
}
