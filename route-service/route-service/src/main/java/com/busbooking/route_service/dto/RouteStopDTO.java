package com.busbooking.route_service.dto;

import java.time.LocalTime;

public class RouteStopDTO {

    private Long cityId;
    private Integer km;
    private LocalTime duration;
    private LocalTime gameDuration;

    public LocalTime getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(LocalTime gameDuration) {
        this.gameDuration = gameDuration;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

}
