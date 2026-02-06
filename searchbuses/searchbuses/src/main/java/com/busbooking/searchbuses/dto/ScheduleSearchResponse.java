package com.busbooking.searchbuses.dto;

public class ScheduleSearchResponse {
    private Long scheduleId;
    private String scheduleName;
    private String departureTime;  // or LocalTime
    private Integer restStops;
    private SeatAvailabilityDTO seatAvailability;
    // getters & setters

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getRestStops() {
        return restStops;
    }

    public void setRestStops(Integer restStops) {
        this.restStops = restStops;
    }

    public SeatAvailabilityDTO getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(SeatAvailabilityDTO seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}