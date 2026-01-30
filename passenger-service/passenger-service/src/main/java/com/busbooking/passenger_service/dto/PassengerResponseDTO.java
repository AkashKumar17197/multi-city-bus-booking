package com.busbooking.passenger_service.dto;

public class PassengerResponseDTO {

    private Long passId;
    private Long pnrId;
    private String passengerName;
    private Integer age;
    private String gender;

    // Getters & Setters

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
}