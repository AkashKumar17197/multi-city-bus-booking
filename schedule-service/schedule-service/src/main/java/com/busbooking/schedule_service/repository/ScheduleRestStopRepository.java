package com.busbooking.schedule_service.repository;

import com.busbooking.schedule_service.entity.ScheduleRestStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRestStopRepository
        extends JpaRepository<ScheduleRestStopEntity, Long> {

    List<ScheduleRestStopEntity> findByScheduleId(Long scheduleId);

    void deleteByScheduleId(Long scheduleId);
}