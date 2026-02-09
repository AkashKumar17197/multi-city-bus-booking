package com.busbooking.seat_allocation_service.service;

import com.busbooking.seat_allocation_service.dto.*;

import java.util.List;

public interface SeatAllocationService {

    SeatAllocationResponseDTO createSeatAllocation(
            SeatAllocationCreateRequestDTO requestDTO
    );

    SeatAllocationResponseDTO bookSeat(
            SeatBookingRequestDTO requestDTO
    );

    SeatAllocationResponseDTO updateSeatAllocation(Long saId, SeatAllocationCreateRequestDTO requestDTO);

    void deleteSeatAllocation(Long saId);

    SeatAllocationResponseDTO getSeatAllocationById(Long saId);

    List<Object[]> getSeatAvailability(Long scheduleId);

    List<SeatAvailabilityDTO> getSeatAvailabilityByScheduleId(Long scheduleId);

    SeatAllocationResponseDTO bookSeatsBulk(Long saId, BookSeatsBulkRequestDTO request);
}