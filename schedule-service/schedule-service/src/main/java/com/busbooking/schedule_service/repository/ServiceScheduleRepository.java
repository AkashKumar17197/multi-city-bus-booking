package com.busbooking.schedule_service.repository;

import com.busbooking.schedule_service.entity.ServiceScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
}