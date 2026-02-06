package com.busbooking.route_service.repository;

import com.busbooking.route_service.dto.RouteStopView;
import com.busbooking.route_service.entity.RouteStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteStopRepository
        extends JpaRepository<RouteStopEntity, Long> {

    List<RouteStopEntity>
    findByRouteIdAndDirectionSeqOrderByRsSeqIdAsc(Long routeId, Integer directionSeq);

    void deleteByRouteId(Long routeId);

    @Query(value = """
        SELECT DISTINCT route_id 
        FROM route_stops_rs 
        WHERE city_id_stop IN :fromCities
        INTERSECT
        SELECT DISTINCT route_id 
        FROM route_stops_rs 
        WHERE city_id_stop IN :toCities
        """, nativeQuery = true)
    List<Long> findRouteIdsByFromAndToCities(@Param("fromCities") List<Long> fromCities,
                                             @Param("toCities") List<Long> toCities);

    @Query(value = """
    SELECT rsrs.route_id              AS routeId,
           rbt.bus_type_name           AS busTypeName,
           rbt.seater_fare             AS seaterFare,
           rbt.sleeper_fare            AS sleeperFare,
           rsrs.city_id_stop           AS cityIdStop,
           rsrs.direction_seq          AS directionSeq,
           rsrs.rs_seq_id              AS rsSeqId,
           rsrs.km                     AS km,
           rsrs.duration               AS duration
    FROM route_stops_rs rsrs
    INNER JOIN routes_r rs ON rsrs.route_id = rs.route_id
    INNER JOIN route_bus_types rbt ON rs.bus_type_id = rbt.bus_type_id
    WHERE rsrs.route_id = :routeId
      AND rsrs.direction_seq = :direction
    ORDER BY rsrs.rs_seq_id
    """, nativeQuery = true)
    List<RouteStopView> findRouteStopsWithFare(@Param("routeId") Long routeId,
                                               @Param("direction") Integer direction);
}