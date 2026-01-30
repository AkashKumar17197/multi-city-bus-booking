package com.busbooking.passenger_service.service;

import com.busbooking.passenger_service.dto.PassengerBookingRequestDTO;
import com.busbooking.passenger_service.dto.PassengerBookingResponseDTO;

public interface PassengerBookingService {

    PassengerBookingResponseDTO createBooking(PassengerBookingRequestDTO request);

    PassengerBookingResponseDTO getBooking(Long pnrId);

    PassengerBookingResponseDTO updateBooking(
            Long pnrId,
            PassengerBookingRequestDTO request
    );

    void deleteBooking(Long pnrId);
}