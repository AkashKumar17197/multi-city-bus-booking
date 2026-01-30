package com.busbooking.passenger_service.repository;

import com.busbooking.passenger_service.entity.PassengerJourneyEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerJourneyRepository extends JpaRepository<PassengerJourneyEntity, Long> {

    List<PassengerJourneyEntity> findBySaId(Long saId);

    List<PassengerJourneyEntity> findByPassId(Long passId);

    List<PassengerJourneyEntity> findBySaIdAndPassId(Long saId, Long passId);

    List<PassengerJourneyEntity> findByPassIdIn(List<Long> passIds);
}