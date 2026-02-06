package com.busbooking.schedule_service.repository;

import com.busbooking.schedule_service.dto.ScheduleSearchResponse;
import com.busbooking.schedule_service.entity.ServiceScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceScheduleRepository
        extends JpaRepository<ServiceScheduleEntity, Long> {

    List<ServiceScheduleEntity> findByServiceId(Long serviceId);

    Optional<ServiceScheduleEntity> findByServiceIdAndDirectionSeq(
            Long serviceId,
            Integer directionSeq
    );

    void deleteByServiceId(Long serviceId);

    @Query("""
        SELECT new com.busbooking.schedule_service.dto.ScheduleSearchResponse(
            sc.routeId,
            ssc.directionSeq,
            ssc.scheduleId,
            ssc.scheduleName,
            ssc.departureTime,
            COUNT(srs.scheduleId)
        )
        FROM ServiceEntity sc
        JOIN ServiceScheduleEntity ssc
            ON sc.serviceId = ssc.serviceId
        LEFT JOIN ScheduleRestStopEntity srs
            ON ssc.scheduleId = srs.scheduleId
        WHERE sc.routeId = :routeId
          AND ssc.directionSeq = :directionSeq
        GROUP BY sc.routeId,
                 ssc.directionSeq,
                 ssc.scheduleId,
                 ssc.scheduleName,
                 ssc.departureTime
        ORDER BY ssc.departureTime
    """)
    List<ScheduleSearchResponse> findSchedulesForSearch(
            @Param("routeId") Long routeId,
            @Param("directionSeq") Integer directionSeq
    );
}