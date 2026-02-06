package com.busbooking.route_service.controller;

import com.busbooking.route_service.dto.*;
import com.busbooking.route_service.entity.RouteEntity;
import com.busbooking.route_service.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<RouteResponseDTO> createRoute(
            @RequestBody RouteRequestDTO dto) {
        return ResponseEntity.ok(routeService.createRoute(dto));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponseDTO> getRoute(
            @PathVariable Long routeId) {
        return ResponseEntity.ok(routeService.getRoute(routeId));
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Long routeId,
            @RequestBody RouteRequestDTO dto) {
        return ResponseEntity.ok(routeService.updateRoute(routeId, dto));
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(
            @PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<RouteSearchResponse> searchRoutes(@RequestBody RouteSearchRequest request) {
        return ResponseEntity.ok(
                routeService.searchRoutes(request.getFromCities(), request.getToCities())
        );
    }

    @GetMapping("/{routeId}/stops")
    public ResponseEntity<List<RouteStopView>> getRouteStops(
            @PathVariable Long routeId,
            @RequestParam Integer direction) {

        return ResponseEntity.ok(
                routeService.getRouteStopsWithFare(routeId, direction)
        );
    }
}
