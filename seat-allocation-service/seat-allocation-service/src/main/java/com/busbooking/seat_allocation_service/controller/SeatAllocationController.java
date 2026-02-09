package com.busbooking.seat_allocation_service.controller;

import com.busbooking.seat_allocation_service.dto.*;
import com.busbooking.seat_allocation_service.service.SeatAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    // UPDATE SEAT ALLOCATION
    // --------------------------------------------------
    @PutMapping("/{saId}")
    public ResponseEntity<SeatAllocationResponseDTO> updateSeatAllocation(
            @PathVariable Long saId,
            @RequestBody SeatAllocationCreateRequestDTO requestDTO
    ) {
        SeatAllocationResponseDTO response =
                seatAllocationService.updateSeatAllocation(saId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------
    // DELETE SEAT ALLOCATION
    // --------------------------------------------------
    @DeleteMapping("/{saId}")
    public ResponseEntity<Void> deleteSeatAllocation(@PathVariable Long saId) {
        seatAllocationService.deleteSeatAllocation(saId);
        return ResponseEntity.noContent().build();
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

    @PostMapping("/{saId}/book-bulk")
    public ResponseEntity<SeatAllocationResponseDTO> bookSeatsBulk(
            @PathVariable Long saId,
            @RequestBody BookSeatsBulkRequestDTO request
    ) {
        SeatAllocationResponseDTO response = seatAllocationService.bookSeatsBulk(saId, request);
        return ResponseEntity.ok(response);
    }



}