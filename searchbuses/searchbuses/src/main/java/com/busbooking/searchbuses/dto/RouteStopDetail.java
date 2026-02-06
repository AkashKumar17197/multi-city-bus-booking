package com.busbooking.searchbuses.dto;

public class RouteStopDetail {
    private Long cityId;
    private String duration;

    public RouteStopDetail(Long cityId, String duration) {
        this.cityId = cityId;
        this.duration = duration;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}