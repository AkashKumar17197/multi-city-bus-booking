package com.busbooking.route_service.dto;

import java.util.List;

public class RouteSearchResponse {
    private List<Long> routeIds;

    public RouteSearchResponse() {
    }

    public RouteSearchResponse(List<Long> routeIds) {
        this.routeIds = routeIds;
    }

    public List<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(List<Long> routeIds) {
        this.routeIds = routeIds;
    }
}