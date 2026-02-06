package com.busbooking.searchbuses.controller;

import com.busbooking.searchbuses.dto.CityResponse;
import com.busbooking.searchbuses.dto.RouteDetailResponse;
import com.busbooking.searchbuses.dto.RouteSearchRequest;
import com.busbooking.searchbuses.dto.RouteSearchResponse;
import com.busbooking.searchbuses.service.CityServiceClient;
import com.busbooking.searchbuses.service.RouteServiceClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/bus-search")
public class RouteSearchController {

    private final CityServiceClient cityServiceClient;
    private final RouteServiceClient routeServiceClient;

    public RouteSearchController(CityServiceClient cityServiceClient,
                                 RouteServiceClient routeServiceClient) {
        this.cityServiceClient = cityServiceClient;
        this.routeServiceClient = routeServiceClient;
    }

    /**
     * Step 2 + Step 3 combined
     */
    @PostMapping("/routes")
    public Flux<RouteDetailResponse> searchRoutes(
            @RequestBody RouteSearchRequest request) {

        // Step 2: get routeIds
        Mono<RouteSearchResponse> routeSearchMono =
                routeServiceClient.searchRoutes(request);

        // Step 3: get route details
        return routeSearchMono.flatMapMany(response -> {

            if (response.getRouteIds() == null ||
                    response.getRouteIds().isEmpty()) {
                return Flux.empty();
            }

            return routeServiceClient.getRouteDetails(
                    response.getRouteIds(),
                    request.getFromCities(),
                    request.getToCities()
            );
        });
    }
}