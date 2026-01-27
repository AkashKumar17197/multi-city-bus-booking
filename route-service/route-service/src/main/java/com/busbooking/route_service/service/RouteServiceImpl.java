package com.busbooking.route_service.service;

import com.busbooking.route_service.dto.RouteRequestDTO;
import com.busbooking.route_service.dto.RouteResponseDTO;
import com.busbooking.route_service.dto.RouteStopDTO;
import com.busbooking.route_service.entity.RouteEntity;
import com.busbooking.route_service.entity.RouteStopEntity;
import com.busbooking.route_service.repository.RouteRepository;
import com.busbooking.route_service.repository.RouteStopRepository;
import com.busbooking.route_service.service.RouteService;
import jakarta.transaction.Transactional;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;

    public RouteServiceImpl(RouteRepository routeRepository,
                            RouteStopRepository routeStopRepository) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
    }

    @Override
    @Transactional
    public RouteResponseDTO createRoute(RouteRequestDTO dto) {

        RouteEntity route = new RouteEntity();
        route.setRouteName(dto.getRouteName());
        route.setBusTypeId(dto.getBusTypeId());
        route.setFromCityId(dto.getFromCityId());
        route.setToCityId(dto.getToCityId());
        route.setCreatedBy(dto.getCreatedBy());

        route = routeRepository.save(route);

        saveStops(route.getRouteId(), dto.getRouteStopsOngoing(), 1);
        saveStops(route.getRouteId(), dto.getRouteStopsReturn(), 2);

        return getRoute(route.getRouteId());
    }

    @Override
    public RouteResponseDTO getRoute(Long routeId) {

        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        RouteResponseDTO response = new RouteResponseDTO();
        response.setRouteId(route.getRouteId());
        response.setRouteName(route.getRouteName());
        response.setBusTypeId(route.getBusTypeId());
        response.setFromCityId(route.getFromCityId());
        response.setToCityId(route.getToCityId());
        response.setStatus(route.getStatus());

        response.setRouteStopsOngoing(
                mapStops(routeStopRepository
                        .findByRouteIdAndDirectionSeqOrderByRsSeqIdAsc(routeId, 1)));

        response.setRouteStopsReturn(
                mapStops(routeStopRepository
                        .findByRouteIdAndDirectionSeqOrderByRsSeqIdAsc(routeId, 2)));

        return response;
    }

    @Override
    @Transactional
    public RouteResponseDTO updateRoute(Long routeId, RouteRequestDTO dto) {

        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        route.setRouteName(dto.getRouteName());
        route.setUpdatedBy(dto.getCreatedBy());

        routeRepository.save(route);

        routeStopRepository.deleteByRouteId(routeId);

        saveStops(routeId, dto.getRouteStopsOngoing(), 1);
        saveStops(routeId, dto.getRouteStopsReturn(), 2);

        return getRoute(routeId);
    }

    @Override
    @Transactional
    public void deleteRoute(Long routeId) {

        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        route.setStatus("DELETED");
        routeRepository.save(route);

        routeStopRepository.deleteByRouteId(routeId);
    }

    // ---------- helper methods ----------

    private void saveStops(Long routeId, List<RouteStopDTO> dtos, int directionSeq) {

        if (dtos == null) return;

        for (RouteStopDTO dto : dtos) {
            RouteStopEntity stop = new RouteStopEntity();
            stop.setRouteId(routeId);
            stop.setDirectionSeq(directionSeq);
            stop.setCityIdStop(dto.getCityId());
            stop.setKm(dto.getKm());
            stop.setDuration(dto.getDuration());
            stop.setGameDuration(dto.getGameDuration());

            routeStopRepository.save(stop);
        }
    }

    private List<RouteStopDTO> mapStops(List<RouteStopEntity> entities) {

        List<RouteStopDTO> list = new ArrayList<>();

        for (RouteStopEntity e : entities) {
            RouteStopDTO dto = new RouteStopDTO();
            dto.setCityId(e.getCityIdStop());
            dto.setKm(e.getKm());
            dto.setDuration(e.getDuration());
            dto.setGameDuration(e.getGameDuration());
            list.add(dto);
        }
        return list;
    }
}