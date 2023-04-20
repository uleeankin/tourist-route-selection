package com.uleeankin.touristrouteselection.services.city;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.repositories.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    @Autowired
    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<City> getAll() {
        return this.repository.findAll();
    }

    @Override
    public boolean save(String cityName) {
        if (this.repository.findByName(cityName).isEmpty()) {
            this.repository.save(cityName);
            return true;
        }
        return false;
    }
}
