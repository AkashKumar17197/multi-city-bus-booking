package com.busbooking.passenger_service.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passengers_list_pl")
public class PassengerListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pass_id")
    private Long passId;

    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    // ---------------- RELATIONSHIPS ----------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pnr_id", nullable = false)
    private PassengerEntity passenger;

    @OneToMany(
            mappedBy = "passengerList",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PassengerJourneyEntity> journeys = new ArrayList<>();

    // ---------- getters & setters ----------

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
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

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    public List<PassengerJourneyEntity> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<PassengerJourneyEntity> journeys) {
        this.journeys = journeys;
    }
}