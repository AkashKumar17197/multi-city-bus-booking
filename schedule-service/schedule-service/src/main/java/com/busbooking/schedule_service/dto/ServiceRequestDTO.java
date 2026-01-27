package com.busbooking.schedule_service.dto;

public class ServiceRequestDTO {

    private Long serviceId;     // null for create, required for update
    private Long routeId;

    private Long driverId1;
    private Long driverId2;
    private Long driverId3;
    private Long driverId4;

    private String status;
    private String createdBy;
    private String updatedBy;

    private ScheduleDTO scheduleOngoing; // direction_seq = 1
    private ScheduleDTO scheduleReturn;  // direction_seq = 2

    // getters & setters

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getDriverId1() {
        return driverId1;
    }

    public void setDriverId1(Long driverId1) {
        this.driverId1 = driverId1;
    }

    public Long getDriverId2() {
        return driverId2;
    }

    public void setDriverId2(Long driverId2) {
        this.driverId2 = driverId2;
    }

    public Long getDriverId3() {
        return driverId3;
    }

    public void setDriverId3(Long driverId3) {
        this.driverId3 = driverId3;
    }

    public Long getDriverId4() {
        return driverId4;
    }

    public void setDriverId4(Long driverId4) {
        this.driverId4 = driverId4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ScheduleDTO getScheduleOngoing() {
        return scheduleOngoing;
    }

    public void setScheduleOngoing(ScheduleDTO scheduleOngoing) {
        this.scheduleOngoing = scheduleOngoing;
    }

    public ScheduleDTO getScheduleReturn() {
        return scheduleReturn;
    }

    public void setScheduleReturn(ScheduleDTO scheduleReturn) {
        this.scheduleReturn = scheduleReturn;
    }
}