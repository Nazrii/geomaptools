package com.mapcalculator.geomaptools.controller;

import com.mapcalculator.geomaptools.model.PostcodeLatLongUpdateRequest;
import com.mapcalculator.geomaptools.model.PostcodeManagerResponse;
import com.mapcalculator.geomaptools.model.PostcodeUpdateRequest;
import com.mapcalculator.geomaptools.service.PostcodeManagerService;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class PostcodeManagerControllerTest {

    private static final String getPostcodeJson = "{ \"postcode\": \"AB10 1XG\"}";
    private static final String updatePostcodeJson = "{ \"old_postcode\": \"AB10aaa\", \"new_postcode\" : \"AB10 1XG\"}";
    private static final String updateLatLongJson = "{ \"postcode\": \"AB10 1XG\", \"new_latitude\": 57.144165, \"new_longitude\": -2.114848}";

    @MockBean
    private PostcodeManagerService postcodeManagerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getPostcodeById success")
    void getPostcodeById_Success() throws Exception {
        Long id = 1l;

        PostcodeManagerResponse response = new PostcodeManagerResponse();
        response.setId(id);
        response.setPostcode("AB10 1XG");
        response.setLatitude(57.144165);
        response.setLongitude(-2.114848);

        when(postcodeManagerService.getPostCodeById(id)).thenReturn(response);

        mockMvc.perform(
                get(String.format("/postcode/id/%s",id))
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.postcode").exists());
    }

    @Test
    @DisplayName("getPostcodeById fail not found due to illegal state exception")
    void getPostcodeById_IllegalStateException_NotFound() throws Exception {
        Long id = 1l;
        when(postcodeManagerService.getPostCodeById(id)).thenThrow(new IllegalStateException());

        mockMvc.perform(
                get(String.format("/postcode/id/",id))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getPostcodeDetail success")
    void getPostcodeDetail_Success() throws Exception {
        String postcode = "AB10 1XG";

        PostcodeManagerResponse response = new PostcodeManagerResponse();
        response.setId(1);
        response.setPostcode(postcode);
        response.setLatitude(57.144165);
        response.setLongitude(-2.114848);

        when(postcodeManagerService.getPostCodeDetail(postcode)).thenReturn(response);

        mockMvc.perform(
                post("/postcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getPostcodeJson)
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("getPostcodeDetail fail not found due to illegal state exception")
    void getPostcodeDetail_IllegalStateException_NotFound() throws Exception {
        String postcode = "AB10 1XG";

        when(postcodeManagerService.getPostCodeDetail(postcode)).thenThrow(new IllegalStateException());
        mockMvc.perform(
                post("/postcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getPostcodeJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updatePostcode success")
    void updatePostcode_Success() throws Exception {

        mockMvc.perform(
                put("/postcode/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePostcodeJson)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("updatePostcode fail not found due to illegal state exception")
    void updatePostcode_IllegalStateException_NotFound() throws Exception {

        doAnswer((i) -> {
            throw new IllegalStateException();
        }).when(postcodeManagerService).updatePostCode(any(PostcodeUpdateRequest.class));
        mockMvc.perform(
                put("/postcode/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePostcodeJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateLatLong success")
    void updateLatLong_Success() throws Exception {
        mockMvc.perform(
                put("/postcode/latlong/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateLatLongJson)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateLatLong fail not found due to illegal state exception")
    void updateLatLong_IllegalStateException_NotFound() throws Exception {
        doAnswer((i) -> {
            throw new IllegalStateException();
        }).when(postcodeManagerService).updateLatLong(any(PostcodeLatLongUpdateRequest.class));
        mockMvc.perform(
                put("/postcode/latlong/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePostcodeJson)
        ).andExpect(status().isNotFound());
    }

}
