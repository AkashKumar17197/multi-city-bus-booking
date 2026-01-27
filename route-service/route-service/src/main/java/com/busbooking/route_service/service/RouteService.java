package com.busbooking.route_service.service;

import com.busbooking.route_service.dto.RouteRequestDTO;
import com.busbooking.route_service.dto.RouteResponseDTO;
import com.busbooking.route_service.entity.RouteEntity;
import org.springframework.util.RouteMatcher;

import java.util.List;

public interface RouteService {

    RouteResponseDTO createRoute(RouteRequestDTO dto);

    RouteResponseDTO getRoute(Long routeId);

    RouteResponseDTO updateRoute(Long routeId, RouteRequestDTO dto);

    void deleteRoute(Long routeId);
}