package com.uleeankin.touristrouteselection.repositories.city;

import com.uleeankin.touristrouteselection.models.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository {

    void save(String cityName);

    Optional<City> findByName(String cityName);

    List<City> findAll();
}
