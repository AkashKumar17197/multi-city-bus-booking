package com.busbooking.searchbuses.controller;

import com.busbooking.searchbuses.dto.RouteDetailResponse;
import com.busbooking.searchbuses.dto.RouteSearchRequest;
import com.busbooking.searchbuses.service.CityServiceClient;
import com.busbooking.searchbuses.service.RouteServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/bus-search")
public class BusSearchUnifiedController {

    private final CityServiceClient cityServiceClient;
    private final RouteServiceClient routeServiceClient;

    public BusSearchUnifiedController(CityServiceClient cityServiceClient,
                                      RouteServiceClient routeServiceClient) {
        this.cityServiceClient = cityServiceClient;
        this.routeServiceClient = routeServiceClient;
    }

    @GetMapping("/searchnormal")
    public Flux<RouteDetailResponse> searchBuses(
            @RequestParam long fromCityId,
            @RequestParam long toCityId,
            @RequestParam String date   // accepted but ignored
    ) {

        // STEP-1: city expansion
        return cityServiceClient.getCities(fromCityId, toCityId)
                .flatMapMany(cityResponse -> {

                    RouteSearchRequest request =
                            new RouteSearchRequest(
                                    cityResponse.getFromCities(),
                                    cityResponse.getToCities()
                            );

                    // STEP-2 → STEP-5
                    return routeServiceClient.searchRoutes(request)
                            .flatMapMany(routeResponse -> {

                                if (routeResponse.getRouteIds() == null ||
                                        routeResponse.getRouteIds().isEmpty()) {
                                    return Flux.empty();
                                }

                                return routeServiceClient.getRouteDetails(
                                        routeResponse.getRouteIds(),
                                        cityResponse.getFromCities(),
                                        cityResponse.getToCities()
                                );
                            });
                });
    }
}