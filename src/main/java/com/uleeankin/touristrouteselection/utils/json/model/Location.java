package com.uleeankin.touristrouteselection.utils.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    @JsonProperty("latLng")
    private List<GeographicalCoordinate> latLng;

}
