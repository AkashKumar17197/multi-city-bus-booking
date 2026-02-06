package com.busbooking.schedule_service.dto;

import java.time.LocalTime;

public class ScheduleSearchResponse {

    private Long routeId;
    private Integer directionSeq;
    private Long scheduleId;
    private String scheduleName;
    private LocalTime departureTime;
    private Long restStops;

    public ScheduleSearchResponse(
            Long routeId,
            Integer directionSeq,
            Long scheduleId,
            String scheduleName,
            LocalTime departureTime,
            Long restStops) {

        this.routeId = routeId;
        this.directionSeq = directionSeq;
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.departureTime = departureTime;
        this.restStops = restStops;
    }

    // getters only (read-only DTO)

    public Long getRouteId() { return routeId; }
    public Integer getDirectionSeq() { return directionSeq; }
    public Long getScheduleId() { return scheduleId; }
    public String getScheduleName() { return scheduleName; }
    public LocalTime getDepartureTime() { return departureTime; }
    public Long getRestStops() { return restStops; }
}