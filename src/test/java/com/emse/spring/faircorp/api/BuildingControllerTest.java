package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.Building;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BuildingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BuildingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BuildingDao buildingDao;

    @MockBean
    private RoomDao roomDao;

    @MockBean
    private WindowDao windowDao;

    @MockBean
    private HeaterDao heaterDao;

    @Test
    void shouldLoadBuildings() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding(20.0),
                createBuilding(17.5)
        ));

        mockMvc.perform(get("/api/buildings").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].outsideTemperature").value(containsInAnyOrder(20.0, 17.5)));
    }

    @Test
    void shouldLoadABuildingAndReturnNullIfNotFound() throws Exception {
        given(buildingDao.findById(999L)).willReturn(new Building());

        mockMvc.perform(get("/api/buildings/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldCreateABuilding() throws Exception {
        Building expectedBuilding = createBuilding(15.8);
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedBuilding));

        given(roomDao.getReferenceById(anyLong())).willReturn(null);
        given(windowDao.getReferenceById(anyLong())).willReturn(null);
        given(heaterDao.getReferenceById(anyLong())).willReturn(null);
        given(buildingDao.save(any())).willReturn(expectedBuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.outsideTemperature").value(15.8));
    }

    @Test
    void shouldDeleteABuilding() throws Exception {
        mockMvc.perform(delete("/api/buildings/999"))
                .andExpect(status().isOk());
    }

    private Building createBuilding(Double temp) {
        return new Building(temp);
    }
}
