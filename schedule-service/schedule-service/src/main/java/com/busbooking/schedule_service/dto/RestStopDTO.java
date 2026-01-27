package com.busbooking.schedule_service.dto;

public class RestStopDTO {

    private Long srsId;          // for update / response
    private Long cityStopId;
    private String locationName;
    private String mapLink;
    private Integer minutes;

    // getters & setters

    public Long getSrsId() {
        return srsId;
    }

    public void setSrsId(Long srsId) {
        this.srsId = srsId;
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