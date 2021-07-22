package com.mapcalculator.geomaptools.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceRequest {
    @JsonProperty("from_postcode")
    private String fromPostcode;
    @JsonProperty("to_postcode")
    private String toPostcode;

    public String getFromPostcode() {
        return fromPostcode;
    }

    public void setFromPostcode(String fromPostcode) {
        this.fromPostcode = fromPostcode;
    }

    public String getToPostcode() {
        return toPostcode;
    }

    public void setToPostcode(String toPostcode) {
        this.toPostcode = toPostcode;
    }
}
