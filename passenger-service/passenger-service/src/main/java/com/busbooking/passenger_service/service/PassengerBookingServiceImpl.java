package com.busbooking.passenger_service.service;

import com.busbooking.passenger_service.dto.*;
import com.busbooking.passenger_service.entity.PassengerEntity;
import com.busbooking.passenger_service.entity.PassengerJourneyEntity;
import com.busbooking.passenger_service.entity.PassengerListEntity;
import com.busbooking.passenger_service.repository.PassengerJourneyRepository;
import com.busbooking.passenger_service.repository.PassengerListRepository;
import com.busbooking.passenger_service.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PassengerBookingServiceImpl implements PassengerBookingService {

    private final PassengerRepository passengerRepository;
    private final PassengerListRepository passengersListRepository;
    private final PassengerJourneyRepository passengerJourneyRepository;

    public PassengerBookingServiceImpl(
            PassengerRepository passengerRepository,
            PassengerListRepository passengersListRepository,
            PassengerJourneyRepository passengerJourneyRepository) {

        this.passengerRepository = passengerRepository;
        this.passengersListRepository = passengersListRepository;
        this.passengerJourneyRepository = passengerJourneyRepository;
    }

    // ---------------- CREATE BOOKING ----------------
    @Override
    public PassengerBookingResponseDTO createBooking(PassengerBookingRequestDTO request) {

        // 1️⃣ Create PNR
        PassengerEntity passengerEntity = new PassengerEntity();
        passengerEntity.setPnrNumber(generatePnr());
        passengerEntity.setPhoneNumber(request.getPhoneNumber());
        passengerEntity.setEmailId(request.getEmailId());
        passengerEntity.setStatus("LIVE");
        passengerEntity.setCreatedBy(request.getCreatedBy());
        passengerEntity.setCreatedDate(LocalDateTime.now());

        passengerEntity = passengerRepository.save(passengerEntity);

        // 2️⃣ Save passengers
        List<PassengerResponseDTO> passengerResponses = new ArrayList<>();
        for (PassengerInfoDTO dto : request.getPassengers()) {

            PassengerListEntity pl = new PassengerListEntity();
            pl.setPnrId(passengerEntity.getPnrId());
            pl.setPassengerName(dto.getPassengerName());
            pl.setAge(dto.getAge());
            pl.setGender(dto.getGender());

            pl = passengersListRepository.save(pl);

            PassengerResponseDTO pr = new PassengerResponseDTO();
            pr.setPassId(pl.getPassId());
            pr.setPnrId(passengerEntity.getPnrId());
            pr.setPassengerName(pl.getPassengerName());
            pr.setAge(pl.getAge());
            pr.setGender(pl.getGender());

            passengerResponses.add(pr);
        }

        // 3️⃣ Save journeys
        List<JourneyResponseDTO> journeyResponses = new ArrayList<>();

        for (JourneySeatRequestDTO journey : request.getJourneys()) {

            JourneyResponseDTO jr = new JourneyResponseDTO();
            jr.setSaId(journey.getSaId());

            List<SeatJourneyResponseDTO> seatResponses = new ArrayList<>();

            if (journey.getSeats() != null) {
                for (SeatPassengerDTO seat : journey.getSeats()) {

                    PassengerJourneyEntity pj = new PassengerJourneyEntity();
                    pj.setSaId(journey.getSaId());
                    pj.setPassId(seat.getPassId());
                    pj.setSeatNo(seat.getSeatNo());

                    pj = passengerJourneyRepository.save(pj);

                    SeatJourneyResponseDTO sr = new SeatJourneyResponseDTO();
                    sr.setPjId(pj.getPjId());
                    sr.setPassId(pj.getPassId());
                    sr.setSeatNo(pj.getSeatNo());

                    seatResponses.add(sr);
                }
            }

            jr.setPassengers(seatResponses);
            journeyResponses.add(jr);
        }

        // 4️⃣ Build response
        PassengerBookingResponseDTO response = new PassengerBookingResponseDTO();
        response.setPnrId(passengerEntity.getPnrId());
        response.setPnrNumber(passengerEntity.getPnrNumber());
        response.setPhoneNumber(passengerEntity.getPhoneNumber());
        response.setEmailId(passengerEntity.getEmailId());
        response.setStatus(passengerEntity.getStatus());
        response.setCreatedDate(passengerEntity.getCreatedDate());
        response.setCreatedBy(passengerEntity.getCreatedBy());
        response.setPassengers(passengerResponses);
        response.setJourneys(journeyResponses);

        return response;
    }

    // ---------------- FETCH BOOKING ----------------
    @Override
    public PassengerBookingResponseDTO getBooking(Long pnrId) {

        // 1️⃣ Fetch PNR
        PassengerEntity pnr = passengerRepository.findById(pnrId)
                .orElseThrow(() -> new RuntimeException("PNR not found"));

        // 2️⃣ Fetch passengers under this PNR
        List<PassengerListEntity> passengers =
                passengersListRepository.findByPnrId(pnrId);

        // 3️⃣ Extract passIds
        List<Long> passIds = new ArrayList<>();
        for (PassengerListEntity p : passengers) {
            passIds.add(p.getPassId());
        }

        // 4️⃣ Fetch journeys using passIds
        List<PassengerJourneyEntity> journeys =
                passengerJourneyRepository.findByPassIdIn(passIds);

        // 5️⃣ Map passengers
        List<PassengerResponseDTO> passengerDTOs = new ArrayList<>();
        for (PassengerListEntity p : passengers) {
            PassengerResponseDTO dto = new PassengerResponseDTO();
            dto.setPassId(p.getPassId());
            dto.setPnrId(p.getPnrId());
            dto.setPassengerName(p.getPassengerName());
            dto.setAge(p.getAge());
            dto.setGender(p.getGender());
            passengerDTOs.add(dto);
        }

        // 6️⃣ Group journeys by sa_id
        Map<Long, List<PassengerJourneyEntity>> journeyMap = new HashMap<>();
        for (PassengerJourneyEntity j : journeys) {
            journeyMap
                    .computeIfAbsent(j.getSaId(), k -> new ArrayList<>())
                    .add(j);
        }

        // 7️⃣ Map journeys
        List<JourneyResponseDTO> journeyResponses = new ArrayList<>();

        for (Map.Entry<Long, List<PassengerJourneyEntity>> entry : journeyMap.entrySet()) {

            JourneyResponseDTO jr = new JourneyResponseDTO();
            jr.setSaId(entry.getKey());

            List<SeatJourneyResponseDTO> seatDTOs = new ArrayList<>();
            for (PassengerJourneyEntity pj : entry.getValue()) {
                SeatJourneyResponseDTO sr = new SeatJourneyResponseDTO();
                sr.setPjId(pj.getPjId());
                sr.setPassId(pj.getPassId());
                sr.setSeatNo(pj.getSeatNo());
                seatDTOs.add(sr);
            }

            jr.setPassengers(seatDTOs);
            journeyResponses.add(jr);
        }

        // 8️⃣ Build final response
        PassengerBookingResponseDTO response = new PassengerBookingResponseDTO();
        response.setPnrId(pnr.getPnrId());
        response.setPnrNumber(pnr.getPnrNumber());
        response.setPhoneNumber(pnr.getPhoneNumber());
        response.setEmailId(pnr.getEmailId());
        response.setStatus(pnr.getStatus());
        response.setCreatedDate(pnr.getCreatedDate());
        response.setCreatedBy(pnr.getCreatedBy());
        response.setUpdatedDate(pnr.getUpdatedDate());
        response.setUpdatedBy(pnr.getUpdatedBy());
        response.setLastUpdatedDate(pnr.getLastUpdatedDate());
        response.setLastUpdatedBy(pnr.getLastUpdatedBy());
        response.setPassengers(passengerDTOs);
        response.setJourneys(journeyResponses);

        return response;
    }

    // ---------------- UPDATE BOOKING ----------------
    @Override
    public PassengerBookingResponseDTO updateBooking(
            Long pnrId,
            PassengerBookingRequestDTO request) {

        PassengerEntity pnr = passengerRepository.findById(pnrId)
                .orElseThrow(() -> new RuntimeException("PNR not found"));

        pnr.setPhoneNumber(request.getPhoneNumber());
        pnr.setEmailId(request.getEmailId());
        pnr.setUpdatedDate(LocalDateTime.now());
        pnr.setUpdatedBy(request.getCreatedBy());

        passengerRepository.save(pnr);

        return getBooking(pnrId);
    }

    // ---------------- DELETE BOOKING (SOFT DELETE) ----------------
    @Override
    public void deleteBooking(Long pnrId) {

        PassengerEntity pnr = passengerRepository.findById(pnrId)
                .orElseThrow(() -> new RuntimeException("PNR not found"));

        pnr.setStatus("CANCELLED");
        pnr.setLastUpdatedDate(LocalDateTime.now());
        pnr.setLastUpdatedBy("SYSTEM");

        passengerRepository.save(pnr);
    }

    // ---------------- UTIL ----------------
    private String generatePnr() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}