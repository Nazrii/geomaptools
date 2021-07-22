package com.mapcalculator.geomaptools.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostcodeUpdateRequest {
    @JsonProperty("old_postcode")
    private String oldPostcode;
    @JsonProperty("new_postcode")
    private String newPostcode;

    public String getOldPostcode() {
        return oldPostcode;
    }

    public void setOldPostcode(String oldPostcode) {
        this.oldPostcode = oldPostcode;
    }

    public String getNewPostcode() {
        return newPostcode;
    }

    public void setNewPostcode(String newPostcode) {
        this.newPostcode = newPostcode;
    }
}
