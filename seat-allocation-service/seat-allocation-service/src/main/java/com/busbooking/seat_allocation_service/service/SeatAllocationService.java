package com.busbooking.seat_allocation_service.service;

import com.busbooking.seat_allocation_service.dto.SeatAllocationCreateRequestDTO;
import com.busbooking.seat_allocation_service.dto.SeatAllocationResponseDTO;
import com.busbooking.seat_allocation_service.dto.SeatAvailabilityDTO;
import com.busbooking.seat_allocation_service.dto.SeatBookingRequestDTO;

import java.util.List;

public interface SeatAllocationService {

    SeatAllocationResponseDTO createSeatAllocation(
            SeatAllocationCreateRequestDTO requestDTO
    );

    SeatAllocationResponseDTO bookSeat(
            SeatBookingRequestDTO requestDTO
    );

    SeatAllocationResponseDTO getSeatAllocationById(Long saId);

    List<Object[]> getSeatAvailability(Long scheduleId);

    List<SeatAvailabilityDTO> getSeatAvailabilityByScheduleId(Long scheduleId);
}