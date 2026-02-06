package com.busbooking.searchbuses.service;

import com.busbooking.searchbuses.dto.*;
import com.busbooking.searchbuses.service.CityServiceClient;
import com.busbooking.searchbuses.service.RouteServiceClient;
import com.busbooking.searchbuses.service.SeatAllocationServiceClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
            LocalDate date   // kept for future use
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

                // Route → Schedule → Seat availability
                .flatMap(route ->
                        Flux.fromIterable(route.getBusSchedules())
                                .flatMap(schedule ->
                                        seatClient.getSeatAvailability(schedule.getScheduleId())
                                                .flatMapMany(Flux::fromIterable)
                                                .map(seat ->
                                                        buildResult(route, schedule, seat)
                                                )
                                )
                );
    }

    // 🧠 Build final search DTO
    private BusSearchResultDTO buildResult(
            RouteDetailResponse route,
            ScheduleSearchResponse schedule,
            SeatAvailabilityDTO seat
    ) {

        BusSearchResultDTO dto = new BusSearchResultDTO();

        double distance = route.getLastKm() - route.getFirstKm();

        LocalTime departure = LocalTime.parse(schedule.getDepartureTime());

        // ✅ FIXED: parse HH:mm safely
        Duration duration = parseDurationHHmm(route.getLastDuration());

        LocalTime arrival = departure.plus(duration);

        dto.setDeparture(departure.toString());
        dto.setArrival(arrival.toString());
        dto.setKm(distance);
        dto.setDistance(distance);

        dto.setBusCode(schedule.getScheduleName());
        dto.setRestStops(schedule.getRestStops());

        dto.setAvailableSeats(seat.getSeatsLeft());
        dto.setWindowSeats(countWindowSeats(seat));

        dto.setDuration(formatDuration(duration));
        dto.setType(route.getBusTypeName());

        dto.setLowerDeckLayout(seat.getLowerDeckLayout());
        dto.setUpperDeckLayout(seat.getUpperDeckLayout());

        dto.setBoardingPoints(buildStops(route.getBoardingPoints()));
        dto.setDroppingPoints(buildStops(route.getDroppingPoints()));

        return dto;
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

    private List<BoardingDroppingPointDTO> buildStops(List<RouteStopDetail> stops) {
        List<BoardingDroppingPointDTO> list = new ArrayList<>();
        int i = 1;

        for (RouteStopDetail s : stops) {
            list.add(new BoardingDroppingPointDTO(
                    i++,
                    "City-" + s.getCityId(), // replace later with City Service
                    s.getDuration()
            ));
        }
        return list;
    }
}