package com.busbooking.schedule_service.dto;

import java.time.LocalTime;
import java.util.List;

public class ScheduleDTO {

    private Long scheduleId;     // generated / used for update
    private String scheduleName;
    private Integer directionSeq;   // 1 = ongoing, 2 = return
    private LocalTime departureTime;
    private String runningStatus;
    private String createdBy;

    private List<RestStopDTO> restStops;

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

    public Integer getDirectionSeq() {
        return directionSeq;
    }

    public void setDirectionSeq(Integer directionSeq) {
        this.directionSeq = directionSeq;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<RestStopDTO> getRestStops() {
        return restStops;
    }

    public void setRestStops(List<RestStopDTO> restStops) {
        this.restStops = restStops;
    }
}