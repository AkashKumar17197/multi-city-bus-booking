package com.busbooking.searchbuses.dto;

import java.util.List;

public class RouteResponse {

    private List<Long> routeIds;

    public RouteResponse() {
    }

    public RouteResponse(List<Long> routeIds) {
        this.routeIds = routeIds;
    }

    public List<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(List<Long> routeIds) {
        this.routeIds = routeIds;
    }
}