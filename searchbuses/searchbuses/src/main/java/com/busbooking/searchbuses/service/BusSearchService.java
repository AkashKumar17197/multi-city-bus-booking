package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.BusSearchStep1And2Response;
import com.busbooking.searchbuses.dto.RouteSearchRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BusSearchService {

    private final CityServiceClient cityServiceClient;
    private final RouteServiceClient routeServiceClient;

    public BusSearchService(CityServiceClient cityServiceClient,
                            RouteServiceClient routeServiceClient) {
        this.cityServiceClient = cityServiceClient;
        this.routeServiceClient = routeServiceClient;
    }

    public Mono<BusSearchStep1And2Response> searchFromCitiesToRoutes(long fromCityId, long toCityId) {
        return cityServiceClient.getCities(fromCityId, toCityId)
                .flatMap(cityResponse -> {
                    RouteSearchRequest request = new RouteSearchRequest(
                            cityResponse.getFromCities(),
                            cityResponse.getToCities()
                    );

                    return routeServiceClient.searchRoutes(request)
                            .map(routeResponse -> new BusSearchStep1And2Response(
                                    cityResponse.getFromCities(),
                                    cityResponse.getToCities(),
                                    routeResponse.getRouteIds()
                            ));
                });
    }
}