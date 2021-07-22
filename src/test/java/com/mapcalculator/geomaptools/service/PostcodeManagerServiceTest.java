package com.mapcalculator.geomaptools.service;

import com.mapcalculator.geomaptools.entity.PostCodeLatLng;
import com.mapcalculator.geomaptools.model.PostcodeLatLongUpdateRequest;
import com.mapcalculator.geomaptools.model.PostcodeUpdateRequest;
import com.mapcalculator.geomaptools.repository.PostcodeManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@WebMvcTest(value = PostcodeManagerService.class)
public class PostcodeManagerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostcodeManagerRepository postcodeManagerRepository;

    private PostcodeManagerService postcodeManagerService;
    private PostCodeLatLng postCodeLatLng;

    @BeforeEach
    void setUp() {
        postcodeManagerService = new PostcodeManagerService(postcodeManagerRepository);
        postCodeLatLng = new PostCodeLatLng();
    }

    @Test
    @DisplayName("getPostcodeById success")
    void getPostcodeById_Success() {
        Long id = 1l;

        postCodeLatLng.setId(1);
        postCodeLatLng.setPostcode("AB10 1XG");
        postCodeLatLng.setLatitude(57.144165);
        postCodeLatLng.setLongitude(-2.114848);

        Optional<PostCodeLatLng> optPostCodeLatLng = Optional.of(postCodeLatLng);

        when(postcodeManagerRepository.findById(id)).thenReturn(optPostCodeLatLng);
        assertThatNoException().isThrownBy(() -> postcodeManagerService.getPostCodeById(id));
    }

    @Test
    @DisplayName("getPostcodeById fail illegal state exception when id not exist")
    void getPostcodeById_NotExist_IllegalStateException() {
        Long id = 1l;

        assertThatThrownBy(() -> postcodeManagerService.getPostCodeById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Sorry, %s is not on our system at the current time", id));
    }

    @Test
    @DisplayName("getPostcodeDetail success")
    void getPostCodeDetail_Success() {
        String postcode = "AB10 1XG";

        postCodeLatLng.setId(1);
        postCodeLatLng.setPostcode(postcode);
        postCodeLatLng.setLatitude(57.144165);
        postCodeLatLng.setLongitude(-2.114848);

        when(postcodeManagerRepository.findByPostcode(postcode)).thenReturn(postCodeLatLng);
        assertThatNoException().isThrownBy(() -> postcodeManagerService.getPostCodeDetail(postcode));
    }

    @Test
    @DisplayName("getPostcodeDetail fail illegal state exception when postcode not exist")
    void getPostcodeDetail_NotExist_IllegalStateException() {

        assertThatThrownBy(() -> postcodeManagerService.getPostCodeDetail("ASDFD"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Sorry, ASDFD is not on our system at the current time");
    }

    @Test
    @DisplayName("updatePostcode success")
    void updatePostcode_Success() {
        String oldPostcode = "AB10 1XG";
        String newPostcode = "AB10SDFD";

        PostcodeUpdateRequest request = new PostcodeUpdateRequest();
        request.setOldPostcode(oldPostcode);
        request.setNewPostcode(newPostcode);

        when(postcodeManagerRepository.findByPostcode(oldPostcode)).thenReturn(new PostCodeLatLng());
        assertThatNoException().isThrownBy(() -> postcodeManagerService.updatePostCode(request));
    }

    @Test
    @DisplayName("updatePostcode fail illegal state exception when postcode not exist")
    void updatePostcode_NotExist_IllegalStateException() {
        String oldPostcode = "AB10 1XG";
        String newPostcode = "AB10SDFD";

        PostcodeUpdateRequest request = new PostcodeUpdateRequest();
        request.setOldPostcode(oldPostcode);
        request.setNewPostcode(newPostcode);

        assertThatThrownBy(() -> postcodeManagerService.updatePostCode(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Sorry, %s is not on our system at the current time", oldPostcode));
    }

    @Test
    @DisplayName("updatePostcode fail illegal state exception when postcode is empty")
    void updatePostcode_IsEmpty_IllegalStateException(){
        String oldPostcode = "AB10 1XG";
        String newPostcode = "";

        PostcodeUpdateRequest request = new PostcodeUpdateRequest();
        request.setOldPostcode(oldPostcode);
        request.setNewPostcode(newPostcode);

        assertThatThrownBy(() -> postcodeManagerService.updatePostCode(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Sorry, postcode must not be empty");
    }

    @Test
    @DisplayName("updateLatLong success")
    void updateLatLong_Success() {
        String postcode = "AB10 1XG";
        double latitude = 57.144165;
        double longitude = -2.114848;

        PostcodeLatLongUpdateRequest request = new PostcodeLatLongUpdateRequest();
        request.setPostcode(postcode);
        request.setNewLatitude(latitude);
        request.setNewLongitude(longitude);

        when(postcodeManagerRepository.findByPostcode(postcode)).thenReturn(new PostCodeLatLng());
        assertThatNoException().isThrownBy(() -> postcodeManagerService.updateLatLong(request));
    }

    @Test
    @DisplayName("updateLatLong fail illegal state exception when postcode not exist")
    void updateLatLong_PostcodeNotExist_IllegalStateException() {
        String postcode = "AB10 1XG";
        double latitude = 57.144165;
        double longitude = -2.114848;

        PostcodeLatLongUpdateRequest request = new PostcodeLatLongUpdateRequest();
        request.setPostcode(postcode);
        request.setNewLatitude(latitude);
        request.setNewLongitude(longitude);

        assertThatThrownBy(() -> postcodeManagerService.updateLatLong(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Sorry, %s is not on our system at the current time", postcode));
    }

    @Test
    @DisplayName("updateLatLong fail illegal state exception when postcode empty")
    void updateLatLong_PostcodeEmpty_IllegalStateException() {
        String postcode = "";
        double latitude = 57.144165;
        double longitude = -2.114848;

        PostcodeLatLongUpdateRequest request = new PostcodeLatLongUpdateRequest();
        request.setPostcode(postcode);
        request.setNewLatitude(latitude);
        request.setNewLongitude(longitude);

        assertThatThrownBy(() -> postcodeManagerService.updateLatLong(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Sorry, postcode must not be empty");
    }
}
