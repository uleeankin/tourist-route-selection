package com.uleeankin.touristrouteselection.city.repository;

import com.uleeankin.touristrouteselection.city.model.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository {

    void save(String cityName);

    Optional<City> findByName(String cityName);

    List<City> findAll();
}
