package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface RoomDaoCustom {
    List<Window> findRoomWindows(Long id);
    List<Heater> findRoomHeaters(Long id);
}
