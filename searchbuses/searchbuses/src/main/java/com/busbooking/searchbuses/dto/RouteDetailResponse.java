package com.busbooking.searchbuses.dto;

import java.util.List;

public class RouteDetailResponse {

    private Long routeId;
    private Integer directionSeq;
    private Double firstKm;
    private Double lastKm;
    private String busTypeName;
    private Double seaterFare;
    private Double sleeperFare;
    private String firstDuration;
    private String lastDuration;
    private List<RouteStopDetail> boardingPoints;
    private List<RouteStopDetail> droppingPoints;
    private List<ScheduleSearchResponse> busSchedules;  // schedules with rest stop count

    public RouteDetailResponse(Long routeId, Integer directionSeq, Double firstKm, Double lastKm,
                               String busTypeName, Double seaterFare, Double sleeperFare,
                               String firstDuration, String lastDuration,
                               List<RouteStopDetail> boardingPoints, List<RouteStopDetail> droppingPoints) {
        this.routeId = routeId;
        this.directionSeq = directionSeq;
        this.firstKm = firstKm;
        this.lastKm = lastKm;
        this.busTypeName = busTypeName;
        this.seaterFare = seaterFare;
        this.sleeperFare = sleeperFare;
        this.firstDuration = firstDuration;
        this.lastDuration = lastDuration;
        this.boardingPoints = boardingPoints;
        this.droppingPoints = droppingPoints;
        this.busSchedules = busSchedules;
    }

    // ===== Getters & Setters =====
    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

    public Integer getDirectionSeq() { return directionSeq; }
    public void setDirectionSeq(Integer directionSeq) { this.directionSeq = directionSeq; }

    public Double getFirstKm() { return firstKm; }
    public void setFirstKm(Double firstKm) { this.firstKm = firstKm; }

    public Double getLastKm() { return lastKm; }
    public void setLastKm(Double lastKm) { this.lastKm = lastKm; }

    public String getBusTypeName() { return busTypeName; }
    public void setBusTypeName(String busTypeName) { this.busTypeName = busTypeName; }

    public Double getSeaterFare() { return seaterFare; }
    public void setSeaterFare(Double seaterFare) { this.seaterFare = seaterFare; }

    public Double getSleeperFare() { return sleeperFare; }
    public void setSleeperFare(Double sleeperFare) { this.sleeperFare = sleeperFare; }

    public String getFirstDuration() { return firstDuration; }
    public void setFirstDuration(String firstDuration) { this.firstDuration = firstDuration; }

    public String getLastDuration() { return lastDuration; }
    public void setLastDuration(String lastDuration) { this.lastDuration = lastDuration; }

    public List<RouteStopDetail> getBoardingPoints() { return boardingPoints; }
    public void setBoardingPoints(List<RouteStopDetail> boardingPoints) { this.boardingPoints = boardingPoints; }

    public List<RouteStopDetail> getDroppingPoints() { return droppingPoints; }
    public void setDroppingPoints(List<RouteStopDetail> droppingPoints) { this.droppingPoints = droppingPoints; }

    public List<ScheduleSearchResponse> getBusSchedules() {
        return busSchedules;
    }

    public void setBusSchedules(List<ScheduleSearchResponse> busSchedules) {
        this.busSchedules = busSchedules;
    }
}