package com.busbooking.route_service.dto;

import java.time.LocalTime;

public interface RouteStopView {

    Long getRouteId();
    String getBusTypeName();
    Double getSeaterFare();
    Double getSleeperFare();
    Long getCityIdStop();
    Integer getDirectionSeq();
    Long getRsSeqId();
    Integer getKm();
    LocalTime getDuration();
}