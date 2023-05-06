package com.uleeankin.touristrouteselection.activity.attributes.coordinates.repository;

import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;

import java.util.Optional;

public interface CoordinateRepository {

    void addCoordinate(Double latitude, Double longitude, Long cityId);

    Optional<Coordinate> getCoordinate(Double latitude, Double longitude);

    void deleteById(Long id);

}
