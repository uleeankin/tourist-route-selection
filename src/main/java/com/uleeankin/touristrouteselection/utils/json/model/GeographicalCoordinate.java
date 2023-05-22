package com.uleeankin.touristrouteselection.utils.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GeographicalCoordinate {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lng")
    private double longitude;

}
