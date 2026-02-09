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

    @Override
    public PassengerBookingResponseDTO createBooking(PassengerBookingRequestDTO request) {

        // 1️⃣ Create PNR entity
        PassengerEntity passengerEntity = new PassengerEntity();
        passengerEntity.setPnrNumber(generatePnr());
        passengerEntity.setPhoneNumber(request.getPhoneNumber());
        passengerEntity.setEmailId(request.getEmailId());
        passengerEntity.setOriginCityId(request.getOriginCityId());
        passengerEntity.setDestinationCityId(request.getDestinationCityId());
        passengerEntity.setStatus("LIVE");
        passengerEntity.setCreatedBy(request.getCreatedBy());
        passengerEntity.setCreatedDate(LocalDateTime.now());

        // 2️⃣ Create passenger list
        List<PassengerListEntity> passengerList = new ArrayList<>();
        for (PassengerInfoDTO dto : request.getPassengers()) {
            PassengerListEntity pl = new PassengerListEntity();
            pl.setPassengerName(dto.getPassengerName());
            pl.setAge(dto.getAge());
            pl.setGender(dto.getGender());
            pl.setPassenger(passengerEntity); // ✅ owns pnr_id
            passengerList.add(pl);
        }
        passengerEntity.setPassengerList(passengerList);

        // 3️⃣ Save PNR + passengers (cascade generates passId)
        passengerEntity = passengerRepository.save(passengerEntity);

        // 4️⃣ Map index → PassengerListEntity
        Map<Integer, PassengerListEntity> passengerIndexMap = new HashMap<>();
        for (int i = 0; i < passengerEntity.getPassengerList().size(); i++) {
            passengerIndexMap.put(i, passengerEntity.getPassengerList().get(i));
        }

        // 5️⃣ Create journeys (seat allocations)
        if (request.getJourneys() != null) {
            for (JourneySeatRequestDTO journeyDTO : request.getJourneys()) {

                if (journeyDTO.getSeats() == null) continue;

                for (int s = 0; s < journeyDTO.getSeats().size(); s++) {
                    SeatPassengerDTO seatDTO = journeyDTO.getSeats().get(s);

                    PassengerListEntity passenger = passengerIndexMap.get(s);
                    if (passenger == null) continue;

                    PassengerJourneyEntity pj = new PassengerJourneyEntity();
                    pj.setSaId(journeyDTO.getSaId());
                    pj.setSeatNo(seatDTO.getSeatNo());

                    // ✅ ONLY relationship sets pass_id
                    pj.setPassengerList(passenger);

                    passenger.getJourneys().add(pj);
                }
            }
        }

        // 6️⃣ Save again to persist journeys
        passengerEntity = passengerRepository.save(passengerEntity);

        // 7️⃣ Extract once (avoids lambda final issue)
        final Long pnrId = passengerEntity.getPnrId();

        // 8️⃣ Build passenger response
        List<PassengerResponseDTO> passengerResponses =
                passengerEntity.getPassengerList().stream()
                        .map(p -> {
                            PassengerResponseDTO pr = new PassengerResponseDTO();
                            pr.setPassId(p.getPassId());
                            pr.setPnrId(pnrId);
                            pr.setPassengerName(p.getPassengerName());
                            pr.setAge(p.getAge());
                            pr.setGender(p.getGender());

                            List<SeatJourneyResponseDTO> seatResponses =
                                    p.getJourneys().stream()
                                            .map(j -> {
                                                SeatJourneyResponseDTO sr = new SeatJourneyResponseDTO();
                                                sr.setPjId(j.getPjId());

                                                // ✅ derive passId via relationship
                                                sr.setPassId(j.getPassengerList().getPassId());

                                                sr.setSeatNo(j.getSeatNo());
                                                return sr;
                                            })
                                            .toList();

                            pr.setSeats(seatResponses);
                            return pr;
                        })
                        .toList();

        // 9️⃣ Final response
        PassengerBookingResponseDTO response = new PassengerBookingResponseDTO();
        response.setPnrId(pnrId);
        response.setPnrNumber(passengerEntity.getPnrNumber());
        response.setPhoneNumber(passengerEntity.getPhoneNumber());
        response.setEmailId(passengerEntity.getEmailId());
        response.setOriginCityId(passengerEntity.getOriginCityId());
        response.setDestinationCityId(passengerEntity.getDestinationCityId());
        response.setStatus(passengerEntity.getStatus());
        response.setCreatedDate(passengerEntity.getCreatedDate());
        response.setCreatedBy(passengerEntity.getCreatedBy());
        response.setPassengers(passengerResponses);

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
                passengersListRepository.findByPassenger_PnrId(pnrId);

        // 3️⃣ Extract passIds
        List<Long> passIds = new ArrayList<>();
        for (PassengerListEntity p : passengers) {
            passIds.add(p.getPassId());
        }

        // 4️⃣ Fetch journeys using passIds
        List<PassengerJourneyEntity> journeys =
                passengerJourneyRepository.findByPassengerList_PassIdIn(passIds);

        // 5️⃣ Map passengers
        List<PassengerResponseDTO> passengerDTOs = new ArrayList<>();
        for (PassengerListEntity p : passengers) {
            PassengerResponseDTO dto = new PassengerResponseDTO();
            dto.setPassId(p.getPassId());
            //dto.setPnrId(p.set);
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
                //sr.setPassId(pj.getPassId());
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
        response.setOriginCityId(pnr.getOriginCityId());
        response.setDestinationCityId(pnr.getDestinationCityId());
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
        pnr.setOriginCityId(request.getOriginCityId());
        pnr.setDestinationCityId(request.getDestinationCityId());
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