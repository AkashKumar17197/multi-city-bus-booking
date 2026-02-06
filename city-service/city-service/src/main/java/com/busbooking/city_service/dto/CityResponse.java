package com.busbooking.city_service.dto;

import java.util.List;

public class CityResponse {

    private List<Long> fromCities;
    private List<Long> toCities;

    // No-args constructor for Jackson
    public CityResponse() {}

    public CityResponse(List<Long> fromCities, List<Long> toCities) {
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