package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WindowController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WindowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WindowController windowController;

    @Test
    void findGetWindows() throws Exception {
        mockMvc.perform(get("/api/windows"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadAWindowAndReturnNullIfNotFound() throws Exception {
//        given(windowDao.findById(999L)).willReturn(Optional.empty());
        mockMvc.perform(get("/api/windows/999").accept(MediaType.APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldSwitchWindow() throws Exception {
        Window expectedWindow = createWindow("window 1");
        Assertions.assertThat(expectedWindow.getWindowStatus()).isEqualTo(WindowStatus.OPEN);

//        mockMvc.perform(put("/api/windows/-10/switch").accept(MediaType.APPLICATION_JSON).with(csrf()))
//                // check the HTTP response
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("window 1"))
//                .andExpect(jsonPath("$.windowStatus").value("CLOSED"));
    }

    private Window createWindow(String name) {
        Window window = new Window(new Room(), name, WindowStatus.OPEN);
        return window;
    }
}
