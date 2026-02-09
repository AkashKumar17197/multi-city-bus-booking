package com.busbooking.searchbuses.mapper;

import com.busbooking.searchbuses.dto.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BusSearchResultMapper {

    public static BusSearchResultDTO map(
            RouteDetailResponse route,
            ScheduleSearchResponse  schedule,
            SeatAvailabilityDTO seat
    ) {

        BusSearchResultDTO dto = new BusSearchResultDTO();

        double distance = route.getLastKm() - route.getFirstKm();

        LocalTime departure = LocalTime.parse(schedule.getDepartureTime());

        Duration first = Duration.parse("PT" + route.getFirstDuration());
        Duration last  = Duration.parse("PT" + route.getLastDuration());

        Duration totalDuration = last.minus(first);

        LocalTime arrival = departure.plus(totalDuration);

        dto.setDeparture(departure.toString());
        dto.setArrival(arrival.toString());
        dto.setKm(distance);
        dto.setDistance(distance);
        dto.setBusCode(schedule.getScheduleName());
        dto.setRestStops(schedule.getRestStops());
        dto.setAvailableSeats(seat.getSeatsLeft());
        dto.setWindowSeats(countWindowSeats(seat));
        dto.setDuration(formatDuration(totalDuration));
        dto.setType(route.getBusTypeName());

        dto.setLowerDeckLayout(seat.getLowerDeckLayout());
        dto.setUpperDeckLayout(seat.getUpperDeckLayout());

        dto.setBoardingPoints(mapStops(route.getBoardingPoints()));
        dto.setDroppingPoints(mapStops(route.getDroppingPoints()));

        return dto;
    }

    private static int countWindowSeats(SeatAvailabilityDTO seat) {
        int count = 0;

        if (seat.getLowerDeckLayout() != null) {
            for (String row : seat.getLowerDeckLayout()) {
                for (String s : row.split(" ")) {
                    if (s.startsWith("ST") || s.startsWith("SL")) {
                        count++;
                    }
                }
            }
        }

        if (seat.getUpperDeckLayout() != null) {
            for (String row : seat.getUpperDeckLayout()) {
                for (String s : row.split(" ")) {
                    if (s.startsWith("ST") || s.startsWith("SL")) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static String formatDuration(Duration d) {
        long hours = d.toHours();
        long mins = d.minusHours(hours).toMinutes();
        return hours + "h " + mins + "m";
    }

    private static List<BoardingDroppingPointDTO> mapStops(
            List<RouteStopDetail> stops
    ) {
        List<BoardingDroppingPointDTO> list = new ArrayList<>();

        if (stops == null) return list;

        int i = 1;
        for (RouteStopDetail s : stops) {
            list.add(new BoardingDroppingPointDTO(
                    i++,
                    "City-" + s.getCityId(), // later replace with city-name
                    s.getSeqId(),
                    s.getDuration()
            ));
        }
        return list;
    }
}