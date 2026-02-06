package com.busbooking.searchbuses.controller;

import com.busbooking.searchbuses.dto.BusSearchStep1And2Response;
import com.busbooking.searchbuses.service.BusSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bus-search")
public class BusSearchController {

    private final BusSearchService busSearchService;

    public BusSearchController(BusSearchService busSearchService) {
        this.busSearchService = busSearchService;
    }

    @GetMapping("/routes-with-cities")
    public Mono<BusSearchStep1And2Response> getRoutesWithCities(
            @RequestParam long fromCityId,
            @RequestParam long toCityId
    ) {
        return busSearchService.searchFromCitiesToRoutes(fromCityId, toCityId);
    }
}