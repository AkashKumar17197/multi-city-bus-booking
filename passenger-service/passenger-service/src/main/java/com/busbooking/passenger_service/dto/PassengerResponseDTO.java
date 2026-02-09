package com.busbooking.passenger_service.dto;

import java.util.List;

public class PassengerResponseDTO {

    private Long passId;
    private Long pnrId;
    private String passengerName;
    private Integer age;
    private String gender;

    // ✅ ADD THIS: seats for this passenger
    private List<SeatJourneyResponseDTO> seats;

    // ---------- Getters & Setters ----------

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public Long getPnrId() {
        return pnrId;
    }

    public void setPnrId(Long pnrId) {
        this.pnrId = pnrId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // ✅ ADD THESE METHODS
    public List<SeatJourneyResponseDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatJourneyResponseDTO> seats) {
        this.seats = seats;
    }
}