package com.mapcalculator.geomaptools.security;

import com.mapcalculator.geomaptools.model.PostcodeManagerResponse;
import com.mapcalculator.geomaptools.service.PostcodeManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class WebSecurityConfigTest {

    private PostcodeManagerResponse response;

    @MockBean
    private PostcodeManagerService postcodeManagerService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        response = new PostcodeManagerResponse();
        response.setId(1l);
        response.setPostcode("AB10 1XG");
        response.setLatitude(57.144165);
        response.setLongitude(-2.114848);
    }

    @WithMockUser
    @Test
    @DisplayName("getPostcodeByID success")
    void testGetPostcodeById_Success() throws Exception {
        Long id = 1l;

        when(postcodeManagerService.getPostCodeById(id)).thenReturn(response);

        mockMvc.perform(
                get(String.format("/postcode/id/%s", id))
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.postcode").exists());

    }

    @Test
    @DisplayName("getPostcodeByID fail unauthorized when no user and password set")
    void testGetPostcodeById_NoUserPassword_Unauthorized() throws Exception {
        Long id = 1l;

        when(postcodeManagerService.getPostCodeById(id)).thenReturn(response);

        mockMvc.perform(
                get(String.format("/postcode/id/%s", id))
        ).andExpect(status().isUnauthorized());

    }
}
