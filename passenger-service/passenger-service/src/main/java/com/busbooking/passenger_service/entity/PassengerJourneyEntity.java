package com.busbooking.passenger_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passenger_journey_pj")
public class PassengerJourneyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pj_id")
    private Long pjId;

    @Column(name = "sa_id", nullable = false)
    private Long saId;

    @Column(name = "seat_no", nullable = false)
    private String seatNo;

    // ❌ REMOVE this field completely
    // private Long passId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_id", nullable = false)
    private PassengerListEntity passengerList;

    // getters & setters
    public Long getPjId() {
        return pjId;
    }

    public void setPjId(Long pjId) {
        this.pjId = pjId;
    }

    public Long getSaId() {
        return saId;
    }

    public void setSaId(Long saId) {
        this.saId = saId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public PassengerListEntity getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(PassengerListEntity passengerList) {
        this.passengerList = passengerList;
    }
}