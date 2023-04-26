package com.uleeankin.touristrouteselection.repositories.coordinate;

import com.uleeankin.touristrouteselection.models.activity.Coordinate;

import java.util.Optional;

public interface CoordinateRepository {

    void addCoordinate(Double latitude, Double longitude, Long cityId);

    Optional<Coordinate> getCoordinate(Double latitude, Double longitude);

}
