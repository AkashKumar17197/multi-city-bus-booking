package com.busbooking.passenger_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passenger_p")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pnr_id")
    private Long pnrId;

    @Column(name = "pnr_number", nullable = false, unique = true)
    private String pnrNumber;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "origin_city_id")
    private Long originCityId;

    @Column(name = "destination_city_id")
    private Long destinationCityId;

    @Column(name = "status", nullable = false)
    private String status;

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

    // ---------------- RELATIONSHIPS ----------------
    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassengerListEntity> passengerList = new ArrayList<>();

    // ---------- getters & setters ----------
    public Long getPnrId() { return pnrId; }
    public void setPnrId(Long pnrId) { this.pnrId = pnrId; }
    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public Long getOriginCityId() { return originCityId; }
    public void setOriginCityId(Long originCityId) { this.originCityId = originCityId; }
    public Long getDestinationCityId() { return destinationCityId; }
    public void setDestinationCityId(Long destinationCityId) { this.destinationCityId = destinationCityId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    public List<PassengerListEntity> getPassengerList() { return passengerList; }
    public void setPassengerList(List<PassengerListEntity> passengerList) { this.passengerList = passengerList; }
}