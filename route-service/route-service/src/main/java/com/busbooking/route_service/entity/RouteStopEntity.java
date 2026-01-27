package com.busbooking.route_service.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "route_stops_rs")
public class RouteStopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rs_seq_id")
    private Long rsSeqId;   // Auto generated + stop order

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "direction_seq", nullable = false)
    private Integer directionSeq; // 1 = ongoing, 2 = return

    public Long getRsSeqId() {
        return rsSeqId;
    }

    public void setRsSeqId(Long rsSeqId) {
        this.rsSeqId = rsSeqId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Integer getDirectionSeq() {
        return directionSeq;
    }

    public void setDirectionSeq(Integer directionSeq) {
        this.directionSeq = directionSeq;
    }

    public Long getCityIdStop() {
        return cityIdStop;
    }

    public void setCityIdStop(Long cityIdStop) {
        this.cityIdStop = cityIdStop;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public LocalTime getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(LocalTime gameDuration) {
        this.gameDuration = gameDuration;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    @Column(name = "city_id_stop", nullable = false)
    private Long cityIdStop;

    @Column(name = "duration", nullable = false)
    private LocalTime duration;

    @Column(name = "game_duration", nullable = false)
    private LocalTime gameDuration;

    @Column(name = "km", nullable = false)
    private Integer km;

    // getters & setters
}