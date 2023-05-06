package com.uleeankin.touristrouteselection.city.service;

import com.uleeankin.touristrouteselection.city.model.City;

import java.util.List;

public interface CityService {

    List<City> getAll();

    boolean save(String cityName);
}
