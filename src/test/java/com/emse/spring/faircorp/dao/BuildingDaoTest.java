package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BuildingDaoTest {
    @Autowired
    private BuildingDao buildingDao;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldFindABuilding() {
        Building building = buildingDao.getReferenceById(-5L);
        Assertions.assertThat(building.getOutsideTemperature()).isEqualTo(10.0);
    }

    @Test
    public void shouldFindBuildingRooms() {
        List<Room> result = buildingDao.findBuildingRooms(-5L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "floor", "name")
                .containsExactly(Tuple.tuple(-10L, 1, "Room1"), Tuple.tuple(-9L, 1, "Room2"));
    }
}
