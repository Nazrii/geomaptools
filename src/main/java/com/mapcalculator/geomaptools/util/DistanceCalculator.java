package com.mapcalculator.geomaptools.util;

import org.springframework.stereotype.Service;

/**
 * This class calculates the distance between two coordinates
 * Coordinates must be provided in a form of latitude and longitude as follows:
 *
 * latitude = 57.144165
 * longitude = -2.114848
 *
 * latitude2 = 57.137880
 * longitude2 = -2.121487
 *
 * This calculation can be tested using DistanceCalculatorTest.java
 */
@Service
public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371; // radius in kilometers

    /**
     * Calculate the following param to retrieve distance using Haversin formula
     * See Wikipedia
     *
     * @param latitude for the first postcode
     * @param longitude for the first postcode
     * @param latitude2 for the second postcode
     * @param longitude2 for the second postcode
     * @return distance
     */
    public double calculate(double latitude, double longitude, double latitude2, double longitude2) {
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }
}
