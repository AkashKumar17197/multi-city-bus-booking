package com.busbooking.city_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cities_c")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city_name", nullable = false, length = 150)
    private String cityName;

    @Column(name = "city_code", nullable = false, length = 10, unique = true)
    private String cityCode;

    @Column(name = "parent_city_id")
    private Long parentCityId;

    @Column(name = "direction_from_state", length = 1)
    private String directionFromState;

    @Column(name = "direction_from_country", length = 1)
    private String directionFromCountry;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    // ===== Getters & Setters =====

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getParentCityId() {
        return parentCityId;
    }

    public void setParentCityId(Long parentCityId) {
        this.parentCityId = parentCityId;
    }

    public String getDirectionFromState() {
        return directionFromState;
    }

    public void setDirectionFromState(String directionFromState) {
        this.directionFromState = directionFromState;
    }

    public String getDirectionFromCountry() {
        return directionFromCountry;
    }

    public void setDirectionFromCountry(String directionFromCountry) {
        this.directionFromCountry = directionFromCountry;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    // ===== JPA callbacks =====

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.status = "LIVE";
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
