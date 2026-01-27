package com.busbooking.route_service.dto;

import java.util.List;

public class RouteResponseDTO {

    private Long routeId;
    private String routeName;
    private Integer busTypeId;
    private Long fromCityId;
    private Long toCityId;
    private String status;

    private List<RouteStopDTO> routeStopsOngoing;
    private List<RouteStopDTO> routeStopsReturn;

    // getters & setters
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getBusTypeId() {
        return busTypeId;
    }

    public void setBusTypeId(Integer busTypeId) {
        this.busTypeId = busTypeId;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RouteStopDTO> getRouteStopsOngoing() {
        return routeStopsOngoing;
    }

    public void setRouteStopsOngoing(List<RouteStopDTO> routeStopsOngoing) {
        this.routeStopsOngoing = routeStopsOngoing;
    }

    public List<RouteStopDTO> getRouteStopsReturn() {
        return routeStopsReturn;
    }

    public void setRouteStopsReturn(List<RouteStopDTO> routeStopsReturn) {
        this.routeStopsReturn = routeStopsReturn;
    }
}