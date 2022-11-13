package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController // (1)
@RequestMapping("/api/rooms") // (2)
@Transactional // (3)
public class RoomController {
    private final RoomDao roomDao;
    private final BuildingDao buildingDao;
    private final WindowDao windowDao;
    private final HeaterDao heaterDao;

    public RoomController(RoomDao roomDao, BuildingDao buildingDao, WindowDao windowDao, HeaterDao heaterDao) {
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
        this.windowDao = windowDao;
        this.heaterDao = heaterDao;
    }

    @GetMapping // (5)
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());  // (6)
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null); // (7)
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        // RoomDto must always contain the building id
        Building building = buildingDao.getById(dto.getBuildingId());
        Room room;
        // On creation id is not defined
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), building, dto.getCurrentTemperature(), dto.getTargetTemperature()));
        }
        else {
            room = roomDao.getById(dto.getId());
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        List<Window> windows = roomDao.findRoomWindows(id);
        for (Window w : windows) {
            windowDao.deleteById(w.getId());
        }

        List<Heater> heaters = roomDao.findRoomHeaters(id);
        for (Heater h : heaters) {
            heaterDao.deleteById(h.getId());
        }

        roomDao.deleteById(id);
    }

    @PutMapping(path = "/{id}/switchWindows")
    public RoomDto switchWindowsStatus(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        List<Window> windows = roomDao.findRoomWindows(id);
        for (Window w : windows) {
            w.setWindowStatus(w.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED: WindowStatus.OPEN);
        }
        room.setWindows(windows);
        return new RoomDto(room);
    }

    @PutMapping(path = "/{id}/switchHeaters")
    public RoomDto switchHeatersStatus(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        List<Heater> heaters = roomDao.findRoomHeaters(id);
        for (Heater h : heaters) {
            h.setHeaterStatus(h.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON);
        }
        room.setHeaters(heaters);
        return new RoomDto(room);
    }

}
