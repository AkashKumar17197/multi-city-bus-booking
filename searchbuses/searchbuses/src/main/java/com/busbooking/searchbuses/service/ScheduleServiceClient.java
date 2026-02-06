package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.ScheduleSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ScheduleServiceClient {

    private final WebClient webClient;

    public ScheduleServiceClient(WebClient.Builder webClientBuilder) {
        // schedule-service is running on port 8083
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8083")
                .build();
    }

    /**
     * Get schedules for a given routeId & directionSeq
     */
    public Flux<ScheduleSearchResponse> getSchedulesForRoute(Long routeId, Integer directionSeq) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/services/search")
                        .queryParam("routeId", routeId)
                        .queryParam("directionSeq", directionSeq)
                        .build()
                )
                .retrieve()
                .bodyToFlux(ScheduleSearchResponse.class);
    }
}