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

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomDaoTest {
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindARoom() {
        Room room = roomDao.getReferenceById(-10L);
        Assertions.assertThat(room.getName()).isEqualTo("Room1");
        Assertions.assertThat(room.getFloor()).isEqualTo(1);
        Assertions.assertThat(room.getBuilding().getId()).isEqualTo(-5L);
    }

    @Test
    public void shouldFindRoomWindows() {
        List<Window> result = roomDao.findRoomWindows(-9L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN), Tuple.tuple(-7L, WindowStatus.CLOSED));
    }

    @Test
    public void shouldFindRoomHeaters() {
        List<Heater> result = roomDao.findRoomHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "power", "heaterStatus")
                .containsExactly(Tuple.tuple(-10L, 2000L, HeaterStatus.ON), Tuple.tuple(-9L, null, HeaterStatus.ON));
    }
}




