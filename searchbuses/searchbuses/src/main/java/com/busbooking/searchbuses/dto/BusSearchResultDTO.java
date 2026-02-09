package com.busbooking.searchbuses.dto;

import java.util.List;

public class BusSearchResultDTO {

    private Long scheduleId;
    private Long saId;
    private String departure;
    private String arrival;
    private double km;
    private String busCode;
    private int restStops;
    private int availableSeats;
    private int windowSeats;
    private double distance;



    private Double seaterFare;
    private Double sleeperFare;
    private String duration;
    private String type;

    private List<String> lowerDeckLayout;
    private List<String> upperDeckLayout;

    private List<String> lowerDeckLayoutSeats;
    private List<String> upperDeckLayoutSeats;

    private List<String> lowerDeckLayoutSeatsOccupied;
    private List<String> upperDeckLayoutSeatsOccupied;

    private List<BoardingDroppingPointDTO> boardingPoints;
    private List<BoardingDroppingPointDTO> droppingPoints;

    // getters & setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getSaId() {
        return saId;
    }

    public void setSaId(Long saId) {
        this.saId = saId;
    }

    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }

    public String getArrival() { return arrival; }
    public void setArrival(String arrival) { this.arrival = arrival; }

    public double getKm() { return km; }
    public void setKm(double km) { this.km = km; }

    public String getBusCode() { return busCode; }
    public void setBusCode(String busCode) { this.busCode = busCode; }

    public int getRestStops() { return restStops; }
    public void setRestStops(int restStops) { this.restStops = restStops; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public int getWindowSeats() { return windowSeats; }
    public void setWindowSeats(int windowSeats) { this.windowSeats = windowSeats; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public Double getSeaterFare() {
        return seaterFare;
    }

    public void setSeaterFare(Double seaterFare) {
        this.seaterFare = seaterFare;
    }

    public Double getSleeperFare() {
        return sleeperFare;
    }

    public void setSleeperFare(Double sleeperFare) {
        this.sleeperFare = sleeperFare;
    }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<String> getLowerDeckLayout() { return lowerDeckLayout; }
    public void setLowerDeckLayout(List<String> lowerDeckLayout) {
        this.lowerDeckLayout = lowerDeckLayout;
    }

    public List<String> getUpperDeckLayout() { return upperDeckLayout; }
    public void setUpperDeckLayout(List<String> upperDeckLayout) {
        this.upperDeckLayout = upperDeckLayout;
    }

    public List<String> getLowerDeckLayoutSeats() {
        return lowerDeckLayoutSeats;
    }

    public void setLowerDeckLayoutSeats(List<String> lowerDeckLayoutSeats) {
        this.lowerDeckLayoutSeats = lowerDeckLayoutSeats;
    }

    public List<String> getUpperDeckLayoutSeats() {
        return upperDeckLayoutSeats;
    }

    public void setUpperDeckLayoutSeats(List<String> upperDeckLayoutSeats) {
        this.upperDeckLayoutSeats = upperDeckLayoutSeats;
    }

    public List<String> getLowerDeckLayoutSeatsOccupied() {
        return lowerDeckLayoutSeatsOccupied;
    }

    public void setLowerDeckLayoutSeatsOccupied(List<String> lowerDeckLayoutSeatsOccupied) {
        this.lowerDeckLayoutSeatsOccupied = lowerDeckLayoutSeatsOccupied;
    }

    public List<String> getUpperDeckLayoutSeatsOccupied() {
        return upperDeckLayoutSeatsOccupied;
    }

    public void setUpperDeckLayoutSeatsOccupied(List<String> upperDeckLayoutSeatsOccupied) {
        this.upperDeckLayoutSeatsOccupied = upperDeckLayoutSeatsOccupied;
    }

    public List<BoardingDroppingPointDTO> getBoardingPoints() { return boardingPoints; }
    public void setBoardingPoints(List<BoardingDroppingPointDTO> boardingPoints) {
        this.boardingPoints = boardingPoints;
    }

    public List<BoardingDroppingPointDTO> getDroppingPoints() { return droppingPoints; }
    public void setDroppingPoints(List<BoardingDroppingPointDTO> droppingPoints) {
        this.droppingPoints = droppingPoints;
    }
}