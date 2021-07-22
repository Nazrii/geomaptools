package com.mapcalculator.geomaptools.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceResponse {
    @JsonProperty("from")
    private Coordinate fromCoordinate;
    @JsonProperty("to")
    private Coordinate toCoordinate;
    private double distance;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Coordinate getFromCoordinate() {
        return fromCoordinate;
    }

    public void setFromCoordinate(Coordinate fromCoordinate) {
        this.fromCoordinate = fromCoordinate;
    }

    public Coordinate getToCoordinate() {
        return toCoordinate;
    }

    public void setToCoordinate(Coordinate toCoordinate) {
        this.toCoordinate = toCoordinate;
    }
}
