package com.mapcalculator.geomaptools.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorTest {

    private DistanceCalculator distanceCalculator;

    @BeforeEach
    void setUp(){
        distanceCalculator = new DistanceCalculator();
    }

    /**
     *  latitude = 57.144165
     *  longitude = -2.114848
     *
     *  latitude2 = 57.137880
     *  longitude2 = -2.121487
     */
    @Test
    void testDistanceCalculator(){

        double latitude = 57.144165;
        double longitude = -2.114848;
        double latitude2 = 57.137880;
        double longitude2 = -2.121487;
        double distance = 0.8055046803242125;

        DecimalFormat df = new DecimalFormat("###.####");
        double calculated = distanceCalculator.calculate(latitude, longitude, latitude2, longitude2);
        assertThat(df.format(calculated)).isEqualTo(df.format(distance));
    }
}
