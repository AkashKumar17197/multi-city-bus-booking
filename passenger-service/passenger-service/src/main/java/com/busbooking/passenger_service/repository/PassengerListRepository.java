package com.busbooking.passenger_service.repository;

import com.busbooking.passenger_service.entity.PassengerJourneyEntity;
import com.busbooking.passenger_service.entity.PassengerListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerListRepository extends JpaRepository<PassengerListEntity, Long> {

    List<PassengerListEntity> findByPnrId(Long pnrId);
}