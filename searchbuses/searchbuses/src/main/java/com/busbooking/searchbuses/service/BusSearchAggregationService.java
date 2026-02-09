package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.*;
import com.busbooking.searchbuses.service.CityServiceClient;
import com.busbooking.searchbuses.service.RouteServiceClient;
import com.busbooking.searchbuses.service.SeatAllocationServiceClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusSearchAggregationService {

    private final CityServiceClient cityServiceClient;
    private final RouteServiceClient routeServiceClient;
    private final SeatAllocationServiceClient seatClient;

    public BusSearchAggregationService(
            CityServiceClient cityServiceClient,
            RouteServiceClient routeServiceClient,
            SeatAllocationServiceClient seatClient
    ) {
        this.cityServiceClient = cityServiceClient;
        this.routeServiceClient = routeServiceClient;
        this.seatClient = seatClient;
    }

    public Flux<BusSearchResultDTO> search(
            long fromCityId,
            long toCityId,
            LocalDate date
    ) {
        return cityServiceClient.getCities(fromCityId, toCityId)
                .flatMapMany(cityResponse ->
                        routeServiceClient.searchRoutes(
                                new RouteSearchRequest(
                                        cityResponse.getFromCities(),
                                        cityResponse.getToCities()
                                )
                        ).flatMapMany(routeResponse ->
                                routeServiceClient.getRouteDetails(
                                        routeResponse.getRouteIds(),
                                        cityResponse.getFromCities(),
                                        cityResponse.getToCities()
                                )
                        )
                )
                .flatMap(route ->
                        Flux.fromIterable(route.getBusSchedules())
                                .flatMap(schedule ->
                                        seatClient.getSeatAvailability(schedule.getScheduleId())
                                                .flatMapMany(Flux::fromIterable)
                                                .flatMap(seat ->
                                                        buildResult(route, schedule, seat)
                                                )
                                )
                );
    }


    // 🧠 Build final search DTO
    private Mono<BusSearchResultDTO> buildResult(
            RouteDetailResponse route,
            ScheduleSearchResponse schedule,
            SeatAvailabilityDTO seat
    ) {

        BusSearchResultDTO dto = new BusSearchResultDTO();

        LocalTime scheduleDeparture =
                LocalTime.parse(schedule.getDepartureTime());

        double distance = route.getLastKm() - route.getFirstKm();

        dto.setScheduleId(schedule.getScheduleId());
        dto.setSaId(seat.getSaId());
        dto.setKm(distance);
        dto.setDistance(distance);
        dto.setSeaterFare(route.getSeaterFare());
        dto.setSleeperFare(route.getSleeperFare());

        dto.setBusCode(schedule.getScheduleName());
        dto.setRestStops(schedule.getRestStops());

        dto.setAvailableSeats(seat.getSeatsLeft());
        dto.setWindowSeats(countWindowSeats(seat));

        dto.setType(route.getBusTypeName());

        dto.setLowerDeckLayout(seat.getLowerDeckLayout());
        dto.setUpperDeckLayout(seat.getUpperDeckLayout());
        dto.setLowerDeckLayoutSeats(seat.getLowerDeckLayoutSeats());
        dto.setUpperDeckLayoutSeats(seat.getUpperDeckLayoutSeats());
        dto.setLowerDeckLayoutSeatsOccupied(seat.getLowerDeckLayoutSeatsOccupied());
        dto.setUpperDeckLayoutSeatsOccupied(seat.getUpperDeckLayoutSeatsOccupied());


        return Mono.zip(
                buildStops(route.getBoardingPoints(), scheduleDeparture),
                buildStops(route.getDroppingPoints(), scheduleDeparture)
        ).map(tuple -> {

            List<BoardingDroppingPointDTO> boarding = tuple.getT1();
            List<BoardingDroppingPointDTO> dropping = tuple.getT2();

            dto.setBoardingPoints(boarding);
            dto.setDroppingPoints(dropping);

            // ✅ departure = first boarding point
            dto.setDeparture(boarding.get(0).getTime());

            // ✅ arrival = last dropping point
            dto.setArrival(dropping.get(dropping.size() - 1).getTime());

            Duration duration = Duration.between(
                    LocalTime.parse(dto.getDeparture()),
                    LocalTime.parse(dto.getArrival())
            );
            dto.setDuration(formatDuration(duration));

            return dto;
        });
    }

    // ✅ NEW: HH:mm → Duration
    private Duration parseDurationHHmm(String hhmm) {
        if (hhmm == null || !hhmm.contains(":")) {
            return Duration.ZERO;
        }

        String[] parts = hhmm.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);

        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    // 🔢 Count window seats
    private int countWindowSeats(SeatAvailabilityDTO seat) {
        int count = 0;

        if (seat.getLowerDeckLayout() != null) {
            for (String row : seat.getLowerDeckLayout()) {
                for (String s : row.split(" ")) {
                    if (s.startsWith("ST") || s.startsWith("SL")) count++;
                }
            }
        }

        if (seat.getUpperDeckLayout() != null) {
            for (String row : seat.getUpperDeckLayout()) {
                for (String s : row.split(" ")) {
                    if (s.startsWith("ST") || s.startsWith("SL")) count++;
                }
            }
        }

        return count;
    }

    private String formatDuration(Duration d) {
        long h = d.toHours();
        long m = d.minusHours(h).toMinutes();
        return h + "h " + m + "m";
    }

    private Mono<List<BoardingDroppingPointDTO>> buildStops(
            List<RouteStopDetail> stops,
            LocalTime scheduleDeparture
    ) {
        return Flux.fromIterable(stops)
                // ✅ SORT BY seqId
                .sort((a, b) -> Long.compare(a.getSeqId(), b.getSeqId()))
                .index()
                .flatMap(tuple -> {
                    int idx = tuple.getT1().intValue();
                    RouteStopDetail stop = tuple.getT2();

                    LocalTime stopTime =
                            calculateStopTime(scheduleDeparture, stop.getDuration());

                    return cityServiceClient.getCityName(stop.getCityId())
                            .map(cityName ->
                                    new BoardingDroppingPointDTO(
                                            idx + 1,            // display order
                                            cityName,
                                            stop.getSeqId(),    // real route sequence
                                            stopTime.toString()
                                    )
                            );
                })
                .collectList();
    }

    private LocalTime calculateStopTime(
            LocalTime departure,
            String durationHHmm
    ) {
        Duration d = parseDurationHHmm(durationHHmm);
        return departure.plus(d);
    }


}