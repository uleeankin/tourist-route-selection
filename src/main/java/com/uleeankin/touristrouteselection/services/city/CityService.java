package com.uleeankin.touristrouteselection.services.city;

import com.uleeankin.touristrouteselection.models.City;

import java.util.List;

public interface CityService {

    List<City> getAll();

    boolean save(String cityName);
}
