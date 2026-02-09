package com.busbooking.seat_allocation_service.dto;

import java.util.List;

public class BookSeatsBulkRequestDTO {

    private Long saId;
    private String busCode;
    private List<SeatDTO> seats;
    private List<PassengerBookingDTO> passengers;
    private ContactDTO contact;
    private BoardingDroppingPointDTO boardingPoint;
    private BoardingDroppingPointDTO droppingPoint;
    private Double totalFare;

    // Getters & Setters
    public Long getSaId() { return saId; }
    public void setSaId(Long saId) { this.saId = saId; }

    public String getBusCode() { return busCode; }
    public void setBusCode(String busCode) { this.busCode = busCode; }

    public List<SeatDTO> getSeats() { return seats; }
    public void setSeats(List<SeatDTO> seats) { this.seats = seats; }

    public List<PassengerBookingDTO> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerBookingDTO> passengers) { this.passengers = passengers; }

    public ContactDTO getContact() { return contact; }
    public void setContact(ContactDTO contact) { this.contact = contact; }

    public BoardingDroppingPointDTO getBoardingPoint() { return boardingPoint; }
    public void setBoardingPoint(BoardingDroppingPointDTO boardingPoint) { this.boardingPoint = boardingPoint; }

    public BoardingDroppingPointDTO getDroppingPoint() { return droppingPoint; }
    public void setDroppingPoint(BoardingDroppingPointDTO droppingPoint) { this.droppingPoint = droppingPoint; }

    public Double getTotalFare() { return totalFare; }
    public void setTotalFare(Double totalFare) { this.totalFare = totalFare; }

    // Inner DTOs
    public static class SeatDTO {
        private String seatNo;
        private String type;

        public String getSeatNo() { return seatNo; }
        public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class ContactDTO {
        private String email;
        private String phone;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static class BoardingDroppingPointDTO {
        private Long id;
        private String point;
        private String time;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getPoint() { return point; }
        public void setPoint(String point) { this.point = point; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
    }
}