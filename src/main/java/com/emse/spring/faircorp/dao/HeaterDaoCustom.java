package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;

import java.util.List;

public interface HeaterDaoCustom {
    int deleteByRoom(Long id);

    List<Heater> findRoomOnHeaters(Long id);
}
