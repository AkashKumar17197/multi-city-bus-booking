package com.busbooking.route_service.dto;

import java.util.List;

public class RouteSearchRequest {
    private List<Long> fromCities;
    private List<Long> toCities;

    public RouteSearchRequest() {
    }

    public RouteSearchRequest(List<Long> fromCities, List<Long> toCities) {
        this.fromCities = fromCities;
        this.toCities = toCities;
    }

    public List<Long> getFromCities() {
        return fromCities;
    }

    public void setFromCities(List<Long> fromCities) {
        this.fromCities = fromCities;
    }

    public List<Long> getToCities() {
        return toCities;
    }

    public void setToCities(List<Long> toCities) {
        this.toCities = toCities;
    }
}