package com.busbooking.passenger_service.repository;

import com.busbooking.passenger_service.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    Optional<PassengerEntity> findByPnrNumber(String pnrNumber);

    boolean existsByPnrNumber(String pnrNumber);
}