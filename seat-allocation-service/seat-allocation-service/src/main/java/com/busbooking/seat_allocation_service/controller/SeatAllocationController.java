package com.busbooking.seat_allocation_service.controller;

import com.busbooking.seat_allocation_service.dto.SeatAllocationCreateRequestDTO;
import com.busbooking.seat_allocation_service.dto.SeatAllocationResponseDTO;
import com.busbooking.seat_allocation_service.dto.SeatAvailabilityDTO;
import com.busbooking.seat_allocation_service.dto.SeatBookingRequestDTO;
import com.busbooking.seat_allocation_service.service.SeatAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-allocations")
public class SeatAllocationController {

    private final SeatAllocationService seatAllocationService;

    public SeatAllocationController(SeatAllocationService seatAllocationService) {
        this.seatAllocationService = seatAllocationService;
    }

    // --------------------------------------------------
    // CREATE SEAT ALLOCATION
    // --------------------------------------------------
    @PostMapping
    public ResponseEntity<SeatAllocationResponseDTO> createSeatAllocation(
            @RequestBody SeatAllocationCreateRequestDTO requestDTO
    ) {
        SeatAllocationResponseDTO response =
                seatAllocationService.createSeatAllocation(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // --------------------------------------------------
    // BOOK SEAT
    // --------------------------------------------------
    @PostMapping("/book")
    public ResponseEntity<SeatAllocationResponseDTO> bookSeat(
            @RequestBody SeatBookingRequestDTO requestDTO
    ) {
        SeatAllocationResponseDTO response =
                seatAllocationService.bookSeat(requestDTO);

        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------
    // GET SEAT ALLOCATION BY ID
    // --------------------------------------------------
    @GetMapping("/{saId}")
    public ResponseEntity<SeatAllocationResponseDTO> getById(
            @PathVariable Long saId
    ) {
        SeatAllocationResponseDTO response =
                seatAllocationService.getSeatAllocationById(saId);

        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------
    // GET SEAT AVAILABILITY BY SCHEDULE
    // --------------------------------------------------
    @GetMapping("/availability/{scheduleId}")
    public ResponseEntity<List<SeatAvailabilityDTO>> getSeatAvailability(
            @PathVariable Long scheduleId) {

        List<SeatAvailabilityDTO> availability =
                seatAllocationService.getSeatAvailabilityByScheduleId(scheduleId);

        return ResponseEntity.ok(availability);
    }


}