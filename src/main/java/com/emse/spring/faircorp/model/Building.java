package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Building {
    @Id
    @GeneratedValue
    private Long id;

    private Double outsideTemperature;

    @OneToMany(mappedBy = "building")
    private Set<Room> rooms;

    public Building() {
    }

    public Building(Double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(Double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
