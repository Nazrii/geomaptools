package com.mapcalculator.geomaptools.service;

import com.mapcalculator.geomaptools.entity.PostCodeLatLng;
import com.mapcalculator.geomaptools.model.DistanceRequest;
import com.mapcalculator.geomaptools.repository.PostcodeManagerRepository;
import com.mapcalculator.geomaptools.util.DistanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@WebMvcTest(value = DistanceService.class)
public class DistanceServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostcodeManagerRepository postcodeManagerRepository;
    @MockBean
    private DistanceCalculator distanceCalculator;
    private DistanceService distanceService;
    private DistanceRequest requestDetail;
    private PostCodeLatLng postCodeLatLng;
    private PostCodeLatLng postCodeLatLng2;

    @BeforeEach
    void setUp() {
        requestDetail = new DistanceRequest();
        distanceService = new DistanceService(postcodeManagerRepository, distanceCalculator);
        postCodeLatLng = new PostCodeLatLng();
        postCodeLatLng2 = new PostCodeLatLng();
    }

    @ParameterizedTest
    @CsvSource({"AB10 1XG, AB10 6RN"})
    @DisplayName("getDistance success")
    void getDistance_Success(String fromPostcode, String toPostcode) {
        requestDetail.setFromPostcode(fromPostcode);
        requestDetail.setToPostcode(toPostcode);

        postCodeLatLng.setLatitude(57.144165);
        postCodeLatLng.setLongitude(-2.114848);

        postCodeLatLng2.setLatitude(57.13788);
        postCodeLatLng2.setLongitude(-2.121487);

        when(postcodeManagerRepository.findByPostcode(fromPostcode)).thenReturn(postCodeLatLng);
        when(postcodeManagerRepository.findByPostcode(toPostcode)).thenReturn(postCodeLatLng2);

        assertThatNoException().isThrownBy(() -> distanceService.getDistance(requestDetail));
    }

    @Test
    @DisplayName("getDistance fail illegal state exception when any postcode is empty")
    void getDistance_PostcodeEmpty_IllegalStateException() {
        requestDetail.setFromPostcode("");
        requestDetail.setToPostcode("");

        assertThatThrownBy(() -> distanceService.getDistance(requestDetail))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Sorry, postcode must not be empty");
    }

    @ParameterizedTest
    @CsvSource({"AB10CC, AB6RN"})
    @DisplayName("getDistance fail illegal state exception when either postcode not found")
    void getDistance_PostcodeNotFound_IllegalStateException(String fromPostcode, String toPostcode) {

        requestDetail.setFromPostcode(fromPostcode);
        requestDetail.setToPostcode(toPostcode);

        when(postcodeManagerRepository.findByPostcode(fromPostcode)).thenReturn(null);

        assertThatThrownBy(() -> distanceService.getDistance(requestDetail))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Sorry, %s is not on our system at the current time", fromPostcode));
    }
}
