package com.busbooking.seat_allocation_service.repository;

import com.busbooking.seat_allocation_service.entity.SeatAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeatAllocationRepository
        extends JpaRepository<SeatAllocationEntity, Long> {

    // Find seat allocation for a schedule on a specific date
    Optional<SeatAllocationEntity> findByScheduleIdAndDateOfJourney(
            Long scheduleId,
            LocalDate dateOfJourney
    );

    // Fetch all active seat allocations
    List<SeatAllocationEntity> findByStatus(String status);

    @Query("""
    SELECT sas.scheduleId, 
           sas.saId, 
           sas.dateOfJourney, 
           sas.lowerDeckLayout, 
           sas.lowerDeckLayoutSeats, 
           sas.lowerDeckLayoutSeatsOccupied, 
           sas.upperDeckLayout, 
           sas.upperDeckLayoutSeats, 
           sas.upperDeckLayoutSeatsOccupied,
           (40 - COUNT(sp.spId)) AS seatsLeft
    FROM SeatAllocationEntity sas
    LEFT JOIN sas.passengerSeats sp
    WHERE sas.scheduleId = :scheduleId
    GROUP BY sas.scheduleId, sas.saId, sas.dateOfJourney,
             sas.lowerDeckLayout, sas.lowerDeckLayoutSeats,
             sas.upperDeckLayout, sas.upperDeckLayoutSeats
""")
    List<Object[]> findSeatAvailabilityByScheduleId(@Param("scheduleId") Long scheduleId);
}