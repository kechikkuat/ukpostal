package com.geopostal.ukpostal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.geopostal.ukpostal.model.Postcode;
import com.geopostal.ukpostal.model.User;
import com.geopostal.ukpostal.services.postcodes.PostcodeService;
import com.geopostal.ukpostal.services.users.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class GeoDistanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostcodeService service;

    @MockBean
    private UserService userService;

    @WithMockUser(username = "admin", password = "admin")
    @Test
    public void findByIdShouldReturnMessageFromService() throws Exception {

        when(service.findById(1L)).thenReturn(Postcode.builder()
                .id(1L)
                .longitude(79.9)
                .latitude(40.5)
                .postcode("ABX1 012")
                .build());
        this.mockMvc.perform(get("/api/geo/{postcodeId}", 1L)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("postcode", Matchers.is("ABX1 012")))
                .andExpect(jsonPath("longitude", Matchers.is(79.9)))
                .andExpect(jsonPath("latitude", Matchers.is(40.5)));
    }
}