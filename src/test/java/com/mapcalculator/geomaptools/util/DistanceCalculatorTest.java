package com.mapcalculator.geomaptools.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceCalculatorTest {

    private DistanceCalculator distanceCalculator;

    /**
     *  latitude = 57.144165
     *  longitude = -2.114848
     *
     *  latitude2 = 57.137880
     *  longitude2 = -2.121487
     */
    private double latitude = 57.144165;
    private double longitude = -2.114848;
    private double latitude2 = 57.137880;
    private double longitude2 = -2.121487;
    private double distance = 0.8055046803242125;

    @BeforeEach
    void setUp(){
        distanceCalculator = new DistanceCalculator();
    }

    @Test
    void testDistanceCalculator(){
        double calculated = distanceCalculator.calculate(latitude, longitude, latitude2, longitude2);
        assertThat(calculated).isEqualTo(distance);
    }
}
