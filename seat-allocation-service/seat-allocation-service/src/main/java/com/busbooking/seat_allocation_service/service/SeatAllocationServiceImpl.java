package com.busbooking.seat_allocation_service.service;

import com.busbooking.seat_allocation_service.dto.*;
import com.busbooking.seat_allocation_service.entity.SeatAllocationEntity;
import com.busbooking.seat_allocation_service.entity.SeatPassengerEntity;
import com.busbooking.seat_allocation_service.repository.SeatAllocationRepository;
import com.busbooking.seat_allocation_service.util.LayoutConverterUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeatAllocationServiceImpl implements SeatAllocationService {

    private final SeatAllocationRepository seatAllocationRepository;

    public SeatAllocationServiceImpl(
            SeatAllocationRepository seatAllocationRepository
    ) {
        this.seatAllocationRepository = seatAllocationRepository;
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

        entity.setStatus("OPEN");
        entity.setCreatedDate(LocalDateTime.now());
        entity.setCreatedBy("SYSTEM");

        SeatAllocationEntity savedEntity =
                seatAllocationRepository.save(entity);

        return mapToResponseDTO(savedEntity);
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
            dto.setUpperDeckLayout(LayoutConverterUtil.splitString((String) row[5]));       // upper_deck_layout
            dto.setUpperDeckLayoutSeats(LayoutConverterUtil.splitString((String) row[6]));  // upper_deck_layout_seats
            dto.setSeatsLeft(((Number) row[7]).intValue());          // seats_left

            result.add(dto);
        }

        return result;
    }
}

