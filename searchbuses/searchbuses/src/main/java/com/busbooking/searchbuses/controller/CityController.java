package com.busbooking.searchbuses.controller;

import com.busbooking.searchbuses.dto.CityResponse;
import com.busbooking.searchbuses.service.CityServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bus-search")
public class CityController {

    private final CityServiceClient cityServiceClient;

    public CityController(CityServiceClient cityServiceClient) {
        this.cityServiceClient = cityServiceClient;
    }

    @GetMapping("/cities")
    public Mono<CityResponse> getCities(
            @RequestParam int fromCityId,
            @RequestParam int toCityId
    ) {
        return cityServiceClient.getCities(fromCityId, toCityId);
    }
}