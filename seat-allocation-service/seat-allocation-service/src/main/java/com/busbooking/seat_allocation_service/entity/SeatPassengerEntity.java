package com.busbooking.seat_allocation_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_passengers_sp")
public class SeatPassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sp_id")
    private Long spId;

    @Column(name = "pj_id", nullable = false)
    private Long pjId;

    @Column(name = "pass_id", nullable = false)
    private Long passId;

    @Column(name = "seat_no", nullable = false)
    private String seatNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sa_id", nullable = false)
    private SeatAllocationEntity seatAllocation;

    // getters & setters
    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    public Long getPjId() {
        return pjId;
    }

    public void setPjId(Long pjId) {
        this.pjId = pjId;
    }

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public SeatAllocationEntity getSeatAllocation() {
        return seatAllocation;
    }

    public void setSeatAllocation(SeatAllocationEntity seatAllocation) {
        this.seatAllocation = seatAllocation;
    }
}