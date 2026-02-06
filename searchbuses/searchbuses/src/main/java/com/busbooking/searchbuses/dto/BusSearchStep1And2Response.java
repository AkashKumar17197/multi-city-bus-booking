package com.busbooking.searchbuses.dto;

import java.util.List;

public class BusSearchStep1And2Response {

    private List<Long> fromCities;
    private List<Long> toCities;
    private List<Long> routeIds;

    public BusSearchStep1And2Response() {}

    public BusSearchStep1And2Response(List<Long> fromCities, List<Long> toCities, List<Long> routeIds) {
        this.fromCities = fromCities;
        this.toCities = toCities;
        this.routeIds = routeIds;
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

    public List<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(List<Long> routeIds) {
        this.routeIds = routeIds;
    }
}