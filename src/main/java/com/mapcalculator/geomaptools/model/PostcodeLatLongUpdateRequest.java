package com.mapcalculator.geomaptools.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostcodeLatLongUpdateRequest {
    private String postcode;
    @JsonProperty("new_latitude")
    private double newLatitude;
    @JsonProperty("new_longitude")
    private double newLongitude;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getNewLatitude() {
        return newLatitude;
    }

    public void setNewLatitude(double newLatitude) {
        this.newLatitude = newLatitude;
    }

    public double getNewLongitude() {
        return newLongitude;
    }

    public void setNewLongitude(double newLongitude) {
        this.newLongitude = newLongitude;
    }
}
