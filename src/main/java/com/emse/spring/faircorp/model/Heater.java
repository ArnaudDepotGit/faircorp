package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
public class Heater {
    @Id // (3)
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long power;


    @ManyToOne
    @JoinColumn(nullable = false)
    private Room room;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeaterStatus heaterStatus;

    public Heater() {
    }

    public Heater(Room room, String name, Long power, HeaterStatus status) {
        this.name = name;
        this.room = room;
        this.power = power;
        this.heaterStatus = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus status) {
        this.heaterStatus = status;
    }
}
