package com.busbooking.schedule_service.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "service_schedules_cs")
public class ServiceScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "schedule_name", nullable = false)
    private String scheduleName;

    @Column(name = "direction_seq", nullable = false)
    private Integer directionSeq; // 1 = ongoing, 2 = return

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "running_status")
    private String runningStatus;

    // ---------- Getters & Setters ----------

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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
}