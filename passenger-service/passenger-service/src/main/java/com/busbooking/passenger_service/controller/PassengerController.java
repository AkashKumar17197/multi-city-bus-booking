package com.busbooking.passenger_service.controller;

import com.busbooking.passenger_service.dto.PassengerBookingRequestDTO;
import com.busbooking.passenger_service.dto.PassengerBookingResponseDTO;
import com.busbooking.passenger_service.service.PassengerBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerBookingService passengerService;

    public PassengerController(PassengerBookingService passengerService) {
        this.passengerService = passengerService;
    }

    // ---------------- CREATE BOOKING ----------------
    @PostMapping("/booking")
    public ResponseEntity<PassengerBookingResponseDTO> createBooking(
            @RequestBody PassengerBookingRequestDTO request) {

        return ResponseEntity.ok(passengerService.createBooking(request));
    }

    // ---------------- FETCH BOOKING ----------------
    @GetMapping("/booking/{pnrId}")
    public ResponseEntity<PassengerBookingResponseDTO> getBooking(
            @PathVariable Long pnrId) {

        return ResponseEntity.ok(passengerService.getBooking(pnrId));
    }

    // ---------------- UPDATE BOOKING ----------------
    @PutMapping("/booking/{pnrId}")
    public ResponseEntity<PassengerBookingResponseDTO> updateBooking(
            @PathVariable Long pnrId,
            @RequestBody PassengerBookingRequestDTO request) {

        return ResponseEntity.ok(passengerService.updateBooking(pnrId, request));
    }

    // ---------------- DELETE BOOKING ----------------
    @DeleteMapping("/booking/{pnrId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long pnrId) {

        passengerService.deleteBooking(pnrId);
        return ResponseEntity.noContent().build();
    }
}
