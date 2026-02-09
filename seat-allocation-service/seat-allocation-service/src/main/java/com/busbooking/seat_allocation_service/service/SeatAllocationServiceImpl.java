package com.busbooking.seat_allocation_service.service;

import com.busbooking.seat_allocation_service.dto.*;
import com.busbooking.seat_allocation_service.entity.SeatAllocationEntity;
import com.busbooking.seat_allocation_service.entity.SeatPassengerEntity;
import com.busbooking.seat_allocation_service.repository.SeatAllocationRepository;
import com.busbooking.seat_allocation_service.util.LayoutConverterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SeatAllocationServiceImpl implements SeatAllocationService {

    private final SeatAllocationRepository seatAllocationRepository;
    private final WebClient webClient;

    public SeatAllocationServiceImpl(
            SeatAllocationRepository seatAllocationRepository,
            WebClient webClient
    ) {
        this.seatAllocationRepository = seatAllocationRepository;
        this.webClient = webClient;
    }

    // --------------------------------------------------
    // CREATE SEAT ALLOCATION
    // --------------------------------------------------
    @Override
    public SeatAllocationResponseDTO createSeatAllocation(
            SeatAllocationCreateRequestDTO requestDTO
    ) {
        SeatAllocationEntity entity = new SeatAllocationEntity();

        entity.setScheduleId(requestDTO.getScheduleId());
        entity.setDateOfJourney(requestDTO.getDateOfJourney());
        entity.setLowerDeckLayout(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayout()));
        entity.setUpperDeckLayout(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayout()));
        entity.setLowerDeckLayoutSeats(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayoutSeats()));
        entity.setUpperDeckLayoutSeats(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayoutSeats()));
        entity.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayoutSeatsOccupied()));
        entity.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayoutSeatsOccupied()));

        entity.setStatus("OPEN");
        entity.setCreatedDate(LocalDateTime.now());
        entity.setCreatedBy("SYSTEM");

        SeatAllocationEntity savedEntity =
                seatAllocationRepository.save(entity);

        return mapToResponseDTO(savedEntity);
    }

    @Override
    public SeatAllocationResponseDTO updateSeatAllocation(Long saId, SeatAllocationCreateRequestDTO requestDTO) {
        SeatAllocationEntity entity = seatAllocationRepository.findById(saId)
                .orElseThrow(() -> new RuntimeException("Seat allocation not found"));

        // Only allow updating layout and status if no passengers have booked seats
        if (entity.getPassengerSeats() != null && !entity.getPassengerSeats().isEmpty()) {
            throw new RuntimeException("Cannot update seat allocation. Seats are already booked.");
        }

        // Update layouts
        entity.setScheduleId(requestDTO.getScheduleId());
        entity.setDateOfJourney(requestDTO.getDateOfJourney());
        entity.setLowerDeckLayout(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayout()));
        entity.setUpperDeckLayout(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayout()));
        entity.setLowerDeckLayoutSeats(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayoutSeats()));
        entity.setUpperDeckLayoutSeats(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayoutSeats()));
        entity.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.joinList(requestDTO.getLowerDeckLayoutSeatsOccupied()));
        entity.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.joinList(requestDTO.getUpperDeckLayoutSeatsOccupied()));

        entity.setUpdatedDate(LocalDateTime.now());
        entity.setUpdatedBy("SYSTEM"); // can be dynamic

        SeatAllocationEntity updatedEntity = seatAllocationRepository.save(entity);
        return mapToResponseDTO(updatedEntity);
    }

    @Override
    public void deleteSeatAllocation(Long saId) {
        SeatAllocationEntity entity = seatAllocationRepository.findById(saId)
                .orElseThrow(() -> new RuntimeException("Seat allocation not found"));

        // Optional: prevent deletion if passengers are booked
        if (entity.getPassengerSeats() != null && !entity.getPassengerSeats().isEmpty()) {
            throw new RuntimeException("Cannot delete seat allocation. Seats are already booked.");
        }

        seatAllocationRepository.delete(entity);
    }

    // --------------------------------------------------
    // BOOK SEAT
    // --------------------------------------------------
    @Override
    public SeatAllocationResponseDTO bookSeat(
            SeatBookingRequestDTO requestDTO
    ) {
        SeatAllocationEntity seatAllocation =
                seatAllocationRepository.findById(requestDTO.getSaId())
                        .orElseThrow(() ->
                                new RuntimeException("Seat allocation not found")
                        );

        // Check if seat already booked
        if (seatAllocation.getPassengerSeats() != null) {
            for (SeatPassengerEntity pse : seatAllocation.getPassengerSeats()) {
                if (pse.getSeatNo().equals(requestDTO.getSeatNo())) {
                    throw new RuntimeException("Seat already booked");
                }
            }
        }

        SeatPassengerEntity passengerSeat = new SeatPassengerEntity();
        passengerSeat.setPjId(requestDTO.getPjId());
        passengerSeat.setPassId(requestDTO.getPassId());
        passengerSeat.setSeatNo(requestDTO.getSeatNo());
        passengerSeat.setSeatAllocation(seatAllocation);

        if (seatAllocation.getPassengerSeats() == null) {
            seatAllocation.setPassengerSeats(new ArrayList<>());
        }

        seatAllocation.getPassengerSeats().add(passengerSeat);

        SeatAllocationEntity updatedEntity =
                seatAllocationRepository.save(seatAllocation);

        return mapToResponseDTO(updatedEntity);
    }

    // --------------------------------------------------
    // GET BY ID
    // --------------------------------------------------
    @Override
    public SeatAllocationResponseDTO getSeatAllocationById(Long saId) {
        Optional<SeatAllocationEntity> optional =
                seatAllocationRepository.findById(saId);

        if (!optional.isPresent()) {
            throw new RuntimeException("Seat allocation not found");
        }

        return mapToResponseDTO(optional.get());
    }

    @Override
    public List<Object[]> getSeatAvailability(Long scheduleId) {
        return seatAllocationRepository.findSeatAvailabilityByScheduleId(scheduleId);
    }

    /*@Override
    public SeatAllocationResponseDTO bookSeatsBulk(Long saId, List<PassengerBookingDTO> passengers) {
        SeatAllocationEntity seatAllocation = seatAllocationRepository.findById(saId)
                .orElseThrow(() -> new RuntimeException("Seat allocation not found"));

        // Convert occupied seats to 2D lists
        List<List<String>> lowerDeckOccupied = LayoutConverterUtil.to2DList(seatAllocation.getLowerDeckLayoutSeatsOccupied());
        List<List<String>> upperDeckOccupied = LayoutConverterUtil.to2DList(seatAllocation.getUpperDeckLayoutSeatsOccupied());

        // Convert seat layouts to 2D lists to find seat positions
        List<List<String>> lowerDeckSeats = LayoutConverterUtil.to2DList(seatAllocation.getLowerDeckLayoutSeats());
        List<List<String>> upperDeckSeats = LayoutConverterUtil.to2DList(seatAllocation.getUpperDeckLayoutSeats());

        // Track seats updated in memory
        for (PassengerBookingDTO passenger : passengers) {
            boolean seatFound = false;

            // ------------------------
            // Check lower deck
            // ------------------------
            for (int i = 0; i < lowerDeckSeats.size(); i++) {
                for (int j = 0; j < lowerDeckSeats.get(i).size(); j++) {
                    if (lowerDeckSeats.get(i).get(j).equals(passenger.getSeatNumber())) {
                        seatFound = true;
                        String current = lowerDeckOccupied.get(i).get(j);

                        if (current.equals("A")) {
                            // Mark seat as booked
                            lowerDeckOccupied.get(i).set(j, passenger.getGender().equalsIgnoreCase("Female") ? "L" : "B");
                        } else {
                            // Seat already booked → rollback all
                            throw new RuntimeException("Seat " + passenger.getSeatNumber() + " already booked");
                        }
                        break;
                    }
                }
                if (seatFound) break;
            }

            // ------------------------
            // Check upper deck if not found in lower deck
            // ------------------------
            if (!seatFound) {
                for (int i = 0; i < upperDeckSeats.size(); i++) {
                    for (int j = 0; j < upperDeckSeats.get(i).size(); j++) {
                        if (upperDeckSeats.get(i).get(j).equals(passenger.getSeatNumber())) {
                            seatFound = true;
                            String current = upperDeckOccupied.get(i).get(j);

                            if (current.equals("A")) {
                                // Mark seat as booked
                                upperDeckOccupied.get(i).set(j, passenger.getGender().equalsIgnoreCase("Female") ? "L" : "B");
                            } else {
                                // Seat already booked → rollback all
                                throw new RuntimeException("Seat " + passenger.getSeatNumber() + " already booked");
                            }
                            break;
                        }
                    }
                    if (seatFound) break;
                }
            }

            // ------------------------
            // If seat number not found in both decks
            // ------------------------
            if (!seatFound) {
                throw new RuntimeException("Seat " + passenger.getSeatNumber() + " not found");
            }
        }

        // ------------------------
        // Update entity with new occupied layout
        // ------------------------
        seatAllocation.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.toStringList(lowerDeckOccupied));
        seatAllocation.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.toStringList(upperDeckOccupied));

        // Save changes
        SeatAllocationEntity updatedEntity = seatAllocationRepository.save(seatAllocation);

        // TODO: Add passenger details to passenger-service module here

        return mapToResponseDTO(updatedEntity);
    }*/

    @Override
    @Transactional
    public SeatAllocationResponseDTO bookSeatsBulk(Long saId, BookSeatsBulkRequestDTO request) {
        // 1️⃣ Fetch seat allocation
        SeatAllocationEntity seatAllocation = seatAllocationRepository.findById(saId)
                .orElseThrow(() -> new RuntimeException("Seat allocation not found"));

        // 2️⃣ Convert occupied seats to 2D lists
        List<List<String>> lowerDeckOccupied = LayoutConverterUtil.to2DList(seatAllocation.getLowerDeckLayoutSeatsOccupied());
        List<List<String>> upperDeckOccupied = LayoutConverterUtil.to2DList(seatAllocation.getUpperDeckLayoutSeatsOccupied());

        // 3️⃣ Convert seat layouts to 2D lists for lookup
        List<List<String>> lowerDeckSeats = LayoutConverterUtil.to2DList(seatAllocation.getLowerDeckLayoutSeats());
        List<List<String>> upperDeckSeats = LayoutConverterUtil.to2DList(seatAllocation.getUpperDeckLayoutSeats());

        // 4️⃣ Prepare PassengerBookingRequestDTO for passenger-service
        PassengerBookingRequestDTO passengerRequest = new PassengerBookingRequestDTO();
        passengerRequest.setPhoneNumber(request.getContact().getPhone());
        passengerRequest.setEmailId(request.getContact().getEmail());
        passengerRequest.setCreatedBy("SYSTEM");

        List<PassengerInfoDTO> passengerInfoList = new ArrayList<>();
        List<JourneySeatRequestDTO> journeyList = new ArrayList<>();

        // 5️⃣ Loop through each passenger and update seat occupancy
        for (PassengerBookingDTO passenger : request.getPassengers()) {
            boolean seatFound = false;

            // --- Lower Deck ---
            for (int i = 0; i < lowerDeckSeats.size(); i++) {
                for (int j = 0; j < lowerDeckSeats.get(i).size(); j++) {
                    if (lowerDeckSeats.get(i).get(j).equals(passenger.getSeatNo())) {
                        seatFound = true;
                        String current = lowerDeckOccupied.get(i).get(j);

                        if (current.equals("A")) {
                            lowerDeckOccupied.get(i).set(j, passenger.getGender().equalsIgnoreCase("Female") ? "L" : "B");
                        } else {
                            throw new RuntimeException("Seat " + passenger.getSeatNo() + " already booked");
                        }
                        break;
                    }
                }
                if (seatFound) break;
            }

            // --- Upper Deck ---
            if (!seatFound) {
                for (int i = 0; i < upperDeckSeats.size(); i++) {
                    for (int j = 0; j < upperDeckSeats.get(i).size(); j++) {
                        if (upperDeckSeats.get(i).get(j).equals(passenger.getSeatNo())) {
                            seatFound = true;
                            String current = upperDeckOccupied.get(i).get(j);

                            if (current.equals("A")) {
                                upperDeckOccupied.get(i).set(j, passenger.getGender().equalsIgnoreCase("Female") ? "L" : "B");
                            } else {
                                throw new RuntimeException("Seat " + passenger.getSeatNo() + " already booked");
                            }
                            break;
                        }
                    }
                    if (seatFound) break;
                }
            }

            if (!seatFound) {
                throw new RuntimeException("Seat " + passenger.getSeatNo() + " not found in layout");
            }

            // --- Prepare passenger-service DTOs ---
            PassengerInfoDTO pi = new PassengerInfoDTO();
            pi.setPassengerName(passenger.getFullName());
            pi.setGender(passenger.getGender());
            pi.setAge(Integer.parseInt(passenger.getAge()));
            passengerInfoList.add(pi);

            SeatPassengerDTO seatDto = new SeatPassengerDTO();
            seatDto.setSeatNo(passenger.getSeatNo());

            JourneySeatRequestDTO journeyDto = new JourneySeatRequestDTO();
            journeyDto.setSaId(saId);
            journeyDto.setSeats(List.of(seatDto));

            journeyList.add(journeyDto);
        }

        passengerRequest.setPassengers(passengerInfoList);
        passengerRequest.setJourneys(journeyList);

        // 6️⃣ Log request payload before calling passenger-service
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestJson = mapper.writeValueAsString(passengerRequest);
            System.out.println("Passenger Service Request Payload: " + requestJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 7️⃣ Update seat allocation with new occupied seats
        seatAllocation.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.join2DList(lowerDeckOccupied));
        seatAllocation.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.join2DList(upperDeckOccupied));

        // 8️⃣ Save updated seat allocation
        SeatAllocationEntity updatedEntity = seatAllocationRepository.save(seatAllocation);

        // 9️⃣ Call passenger-service via WebClient with proper lambda for status check
        Map<String, Object> passengerResponse;
        try {
            passengerResponse = webClient.post()
                    .uri("http://localhost:8084/api/passengers/booking")
                    .bodyValue(passengerRequest)
                    .retrieve()
                    .onStatus(status -> status.is5xxServerError(), response ->
                            response.bodyToMono(String.class)
                                    .doOnNext(body -> System.err.println("Passenger service 500 error body: " + body))
                                    .flatMap(body -> Mono.error(new RuntimeException("Passenger service returned 500: " + body)))
                    )
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            System.out.println("Passenger Service Response: " + passengerResponse);
        } catch (Exception e) {
            System.err.println("Error calling passenger service: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        // 🔟 Return updated seat allocation DTO
        return mapToResponseDTO(updatedEntity);
    }

    // --------------------------------------------------
    // ENTITY → DTO MAPPING
    // --------------------------------------------------
    private SeatAllocationResponseDTO mapToResponseDTO(
            SeatAllocationEntity entity
    ) {
        SeatAllocationResponseDTO dto = new SeatAllocationResponseDTO();

        dto.setSaId(entity.getSaId());
        dto.setScheduleId(entity.getScheduleId());
        dto.setDateOfJourney(entity.getDateOfJourney());
        dto.setLowerDeckLayout(LayoutConverterUtil.splitString(entity.getLowerDeckLayout()));
        dto.setUpperDeckLayout(LayoutConverterUtil.splitString(entity.getUpperDeckLayout()));
        dto.setLowerDeckLayoutSeats(LayoutConverterUtil.splitString(entity.getLowerDeckLayoutSeats()));
        dto.setUpperDeckLayoutSeats(LayoutConverterUtil.splitString(entity.getUpperDeckLayoutSeats()));
        dto.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.splitString(entity.getLowerDeckLayoutSeatsOccupied()));
        dto.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.splitString(entity.getUpperDeckLayoutSeatsOccupied()));
        dto.setStatus(entity.getStatus());

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());

        if (entity.getPassengerSeats() != null) {
            List<PassengerSeatDTO> passengerDTOs = new ArrayList<>();

            for (SeatPassengerEntity pse : entity.getPassengerSeats()) {
                PassengerSeatDTO psDTO = new PassengerSeatDTO();
                psDTO.setPjId(pse.getPjId());
                psDTO.setPassId(pse.getPassId());
                psDTO.setSeatNo(pse.getSeatNo());
                passengerDTOs.add(psDTO);
            }

            dto.setPassengers(passengerDTOs);
        }

        return dto;
    }

    @Override
    public List<SeatAvailabilityDTO> getSeatAvailabilityByScheduleId(Long scheduleId) {
        List<Object[]> rows = seatAllocationRepository.findSeatAvailabilityByScheduleId(scheduleId);
        List<SeatAvailabilityDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            SeatAvailabilityDTO dto = new SeatAvailabilityDTO();

            dto.setScheduleId(((Number) row[0]).longValue());        // sas.schedule_id
            dto.setSaId(((Number) row[1]).longValue());              // sas.sa_id
            dto.setDateOfJourney(row[2].toString());                 // sas.date_of_journey
            dto.setLowerDeckLayout(LayoutConverterUtil.splitString((String) row[3]));       // lower_deck_layout
            dto.setLowerDeckLayoutSeats(LayoutConverterUtil.splitString((String) row[4]));  // lower_deck_layout_seats
            dto.setLowerDeckLayoutSeatsOccupied(LayoutConverterUtil.splitString((String) row[5]));  // lower_deck_layout_seats_occupied
            dto.setUpperDeckLayout(LayoutConverterUtil.splitString((String) row[6]));       // upper_deck_layout
            dto.setUpperDeckLayoutSeats(LayoutConverterUtil.splitString((String) row[7]));  // upper_deck_layout_seats
            dto.setUpperDeckLayoutSeatsOccupied(LayoutConverterUtil.splitString((String) row[8]));  // upper_deck_layout_seats_occupied
            dto.setSeatsLeft(((Number) row[9]).intValue());          // seats_left

            result.add(dto);
        }

        return result;
    }
}

