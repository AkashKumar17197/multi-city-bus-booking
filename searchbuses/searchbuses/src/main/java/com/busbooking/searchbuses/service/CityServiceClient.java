package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.CityResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CityServiceClient {

    private final WebClient webClient;

    public CityServiceClient(WebClient.Builder webClientBuilder) {
        // city-service is running on port 8081
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .build();
    }

    /**
     * Step 1: Get fromCities and toCities from city-service
     */
    public Mono<CityResponse> getCities(long fromCityId, long toCityId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cities/route")
                        .queryParam("fromCityId", fromCityId)
                        .queryParam("toCityId", toCityId)
                        .build()
                )
                .retrieve()
                .bodyToMono(CityResponse.class);
    }
}
