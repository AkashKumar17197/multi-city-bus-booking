package com.busbooking.searchbuses.dto;

public class BusScheduleResponse {
    private long scheduleId;
    private String scheduleName;
    private String departureTime;
    private int restStops;

    // getters & setters
    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
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

    public int getRestStops() {
        return restStops;
    }

    public void setRestStops(int restStops) {
        this.restStops = restStops;
    }
}