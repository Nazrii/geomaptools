package com.mapcalculator.geomaptools.controller;

import com.mapcalculator.geomaptools.model.DistanceRequest;
import com.mapcalculator.geomaptools.model.DistanceResponse;
import com.mapcalculator.geomaptools.service.DistanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class DistanceControllerTest {

    private static final String requestJson = "{\"from_postcode\" : \"AB10 1XG\",\"to_postcode\" : \"AB10 6RN\"}";

    @MockBean
    private DistanceService distanceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getDistance success")
    void getDistance_Success() throws Exception {
        DistanceResponse responseDetail = new DistanceResponse();
        responseDetail.setDistance(8);

        when(distanceService.getDistance(any(DistanceRequest.class))).thenReturn(responseDetail);

        mockMvc.perform(
                post("/distance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.distance").exists());
    }

    @Test
    @DisplayName("getDistance fail not found when illegal state exception occurs")
    void getDistance_IllegalStateException_NotFound() throws Exception {
        when(distanceService.getDistance(any(DistanceRequest.class))).thenThrow(new IllegalStateException());
        mockMvc.perform(
                post("/distance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getDistance fail bad request when empty body is sent")
    void getDistance_EmptyBody_BadRequest() throws Exception {
        mockMvc.perform(
                post("/distance")
        ).andExpect(status().isBadRequest());
    }
}
