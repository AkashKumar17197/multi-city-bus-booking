package com.busbooking.schedule_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule_rest_stops_srs")
public class ScheduleRestStopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srs_id")
    private Long srsId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "city_stop_id", nullable = false)
    private Long cityStopId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "map_link")
    private String mapLink;

    @Column(name = "minutes", nullable = false)
    private Integer minutes;

    // ---------- Getters & Setters ----------

    public Long getSrsId() {
        return srsId;
    }

    public void setSrsId(Long srsId) {
        this.srsId = srsId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getCityStopId() {
        return cityStopId;
    }

    public void setCityStopId(Long cityStopId) {
        this.cityStopId = cityStopId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}