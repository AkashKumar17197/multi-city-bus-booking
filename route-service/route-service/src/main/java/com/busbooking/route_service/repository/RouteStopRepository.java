package com.busbooking.route_service.repository;

import com.busbooking.route_service.entity.RouteStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository
        extends JpaRepository<RouteStopEntity, Long> {

    List<RouteStopEntity>
    findByRouteIdAndDirectionSeqOrderByRsSeqIdAsc(Long routeId, Integer directionSeq);

    void deleteByRouteId(Long routeId);
}