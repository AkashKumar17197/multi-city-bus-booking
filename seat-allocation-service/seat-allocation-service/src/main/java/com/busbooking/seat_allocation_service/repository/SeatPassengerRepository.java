package com.busbooking.seat_allocation_service.repository;

import com.busbooking.seat_allocation_service.entity.SeatPassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatPassengerRepository
        extends JpaRepository<SeatPassengerEntity, Long> {

    // Seat already booked?
    Optional<SeatPassengerEntity> findBySeatAllocation_SaIdAndSeatNo(
            Long saId,
            String seatNo
    );

    // All seats for a seat allocation
    List<SeatPassengerEntity> findBySeatAllocation_SaId(Long saId);

    // Seats by passenger journey
    List<SeatPassengerEntity> findByPjId(Long pjId);
}