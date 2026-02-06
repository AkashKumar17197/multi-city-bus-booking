package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.SeatAvailabilityDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SeatAllocationServiceClient {

    private final WebClient webClient;

    public SeatAllocationServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8086") // seat-allocation-service
                .build();
    }

    public Mono<List<SeatAvailabilityDTO>> getSeatAvailability(Long scheduleId) {
        return webClient.get()
                .uri("/api/seat-allocations/availability/{scheduleId}", scheduleId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SeatAvailabilityDTO>>() {});
    }
}