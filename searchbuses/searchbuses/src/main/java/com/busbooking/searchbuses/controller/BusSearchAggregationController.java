package com.busbooking.searchbuses.controller;

import com.busbooking.searchbuses.dto.BusSearchResultDTO;
import com.busbooking.searchbuses.service.BusSearchAggregationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bus-search")
public class BusSearchAggregationController {

    private final BusSearchAggregationService aggregationService;

    public BusSearchAggregationController(
            BusSearchAggregationService aggregationService
    ) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/search")
    public Flux<BusSearchResultDTO> search(
            @RequestParam long fromCityId,
            @RequestParam long toCityId,
            @RequestParam LocalDate date
    ) {
        return aggregationService.search(fromCityId, toCityId, date);
    }
}