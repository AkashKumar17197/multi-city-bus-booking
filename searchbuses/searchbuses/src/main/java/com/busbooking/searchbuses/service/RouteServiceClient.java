package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RouteServiceClient {

    private final WebClient webClient;
    private final ScheduleServiceClient scheduleServiceClient;
    private final SeatAllocationServiceClient seatAllocationServiceClient;

    public RouteServiceClient(WebClient.Builder webClientBuilder,
                              ScheduleServiceClient scheduleServiceClient,
                              SeatAllocationServiceClient seatAllocationServiceClient) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8081") // route-service URL
                .build();
        this.scheduleServiceClient = scheduleServiceClient;
        this.seatAllocationServiceClient = seatAllocationServiceClient;
    }

    /**
     * Step 2: Search routes based on fromCities and toCities
     */
    public Mono<RouteSearchResponse> searchRoutes(RouteSearchRequest request) {
        return webClient.post()
                .uri("/api/routes/search")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RouteSearchResponse.class);
    }

    /**
     * Step 3 + Step 4 + Step 5:
     * Route → Stops → Schedules → Seat availability
     */
    public Flux<RouteDetailResponse> getRouteDetails(
            List<Long> routeIds,
            List<Long> fromCities,
            List<Long> toCities) {

        return Flux.fromIterable(routeIds)
                .flatMap(routeId ->
                        Flux.fromIterable(List.of(1, 2))
                                .flatMap(direction ->
                                        webClient.get()
                                                .uri("/api/routes/{routeId}/stops?direction={direction}",
                                                        routeId, direction)
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                                                .flatMapMany(stops -> {

                                                    if (stops == null || stops.isEmpty())
                                                        return Flux.empty();

                                                    // ---- collect cityIds ----
                                                    List<Long> cityStops = stops.stream()
                                                            .map(s -> ((Number) s.get("cityIdStop")).longValue())
                                                            .toList();

                                                    int fromIndex = -1, toIndex = -1;

                                                    for (int i = 0; i < cityStops.size(); i++) {
                                                        if (fromCities.contains(cityStops.get(i))) {
                                                            fromIndex = i;
                                                            break;
                                                        }
                                                    }

                                                    if (fromIndex != -1) {
                                                        for (int j = fromIndex + 1; j < cityStops.size(); j++) {
                                                            if (toCities.contains(cityStops.get(j))) {
                                                                toIndex = j;
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    if (fromIndex == -1 || toIndex == -1)
                                                        return Flux.empty();

                                                    // ---- distance & fare ----
                                                    double startKm = ((Number) stops.get(fromIndex).get("km")).doubleValue();
                                                    double endKm = ((Number) stops.get(toIndex).get("km")).doubleValue();

                                                    String startDuration = (String) stops.get(fromIndex).get("duration");
                                                    String endDuration = (String) stops.get(toIndex).get("duration");

                                                    String busTypeName =
                                                            ((String) stops.get(0).get("busTypeName")).trim();

                                                    Double seaterFarePerKm = getDouble(stops.get(0), "seaterFare");
                                                    Double sleeperFarePerKm = getDouble(stops.get(0), "sleeperFare");

                                                    // ---- FIX 1: collect ALL stops ----
                                                    List<RouteStopDetail> boardingStops = new ArrayList<>();
                                                    List<RouteStopDetail> droppingStops = new ArrayList<>();

                                                    for (int i = 0; i < cityStops.size(); i++) {
                                                        Map<String, Object> stop = stops.get(i);
                                                        Long cityId =
                                                                ((Number) stop.get("cityIdStop")).longValue();
                                                        Long seqId =
                                                                ((Number) stop.get("rsSeqId")).longValue();
                                                        String duration =
                                                                (String) stop.get("duration");

                                                        if (fromCities.contains(cityStops.get(i))) {
                                                            boardingStops.add(
                                                                    new RouteStopDetail(cityId, seqId, duration)
                                                            );
                                                        }
                                                        if (toCities.contains(cityStops.get(i))) {
                                                            droppingStops.add(
                                                                    new RouteStopDetail(cityId, seqId, duration)
                                                            );
                                                        }
                                                    }

                                                    /*for (int i = fromIndex; i <= toIndex; i++) {
                                                        Map<String, Object> stop = stops.get(i);
                                                        Long cityId =
                                                                ((Number) stop.get("cityIdStop")).longValue();
                                                        String duration =
                                                                (String) stop.get("duration");

                                                        if (i < toIndex) {
                                                            boardingStops.add(
                                                                    new RouteStopDetail(cityId, duration)
                                                            );
                                                        }
                                                        if (i > fromIndex) {
                                                            droppingStops.add(
                                                                    new RouteStopDetail(cityId, duration)
                                                            );
                                                        }
                                                    }*/


                                                    RouteDetailResponse response =
                                                            new RouteDetailResponse(
                                                                    routeId,
                                                                    direction,
                                                                    startKm,
                                                                    endKm,
                                                                    busTypeName,
                                                                    seaterFarePerKm == null
                                                                            ? null
                                                                            : seaterFarePerKm * (endKm - startKm),
                                                                    sleeperFarePerKm == null
                                                                            ? null
                                                                            : sleeperFarePerKm * (endKm - startKm),
                                                                    startDuration,
                                                                    endDuration,
                                                                    boardingStops,
                                                                    droppingStops
                                                            );

                                                    // ---- schedules + seats (unchanged) ----
                                                    return scheduleServiceClient
                                                            .getSchedulesForRoute(routeId, direction)
                                                            .flatMap(schedule ->
                                                                    seatAllocationServiceClient
                                                                            .getSeatAvailability(schedule.getScheduleId())
                                                                            .map(seats -> {
                                                                                if (seats != null && !seats.isEmpty()) {
                                                                                    schedule.setSeatAvailability(seats.get(0));
                                                                                }
                                                                                return schedule;
                                                                            })
                                                            )
                                                            .collectList()
                                                            .map(schedules -> {
                                                                response.setBusSchedules(schedules);
                                                                return response;
                                                            })
                                                            .flux();
                                                })
                                )
                );
    }

    private Double getDouble(Map<String, Object> map, String key) {
        Object v = map.get(key);
        return v instanceof Number ? ((Number) v).doubleValue() : null;
    }
}