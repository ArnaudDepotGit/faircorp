package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class HeaterDaoTest {
    @Autowired
    private HeaterDao heaterDao;
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAHeater() {
        Heater heater = heaterDao.getReferenceById(-10L);
        Assertions.assertThat(heater.getName()).isEqualTo("Heater1");
        Assertions.assertThat(heater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);
    }

    @Test
    public void shouldDeleteHeatersRoom() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getHeaters().stream().map(Heater::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        heaterDao.deleteByRoom(-10L);
        List<Heater> result = heaterDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindRoomOnHeaters() {
        List<Heater> result = heaterDao.findRoomOnHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "heaterStatus")
                .containsExactly(Tuple.tuple(-10L, HeaterStatus.ON),Tuple.tuple(-9L, HeaterStatus.ON));
    }

    @Test
    public void shouldNotFindRoomOnHeaters() {
        List<Heater> result = heaterDao.findRoomOnHeaters(-9L);
        Assertions.assertThat(result).isEmpty();
    }
}
