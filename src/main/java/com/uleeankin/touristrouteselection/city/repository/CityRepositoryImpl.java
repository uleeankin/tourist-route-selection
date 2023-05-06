package com.uleeankin.touristrouteselection.city.repository;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.city.config.CityConfig;
import com.uleeankin.touristrouteselection.city.mapper.CityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private final CityConfig cityConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityRepositoryImpl(CityConfig cityConfig, JdbcTemplate jdbcTemplate) {
        this.cityConfig = cityConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String cityName) {
        this.jdbcTemplate.update(
                this.cityConfig.getSave(), cityName);
    }

    @Override
    public Optional<City> findByName(String cityName) {
        try {
            City city = this.jdbcTemplate.queryForObject(
                    this.cityConfig.getOne(), new CityRowMapper(), cityName);
            return Optional.ofNullable(city);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<City> findAll() {
        return this.jdbcTemplate.query(
                this.cityConfig.getAll(), new CityRowMapper());
    }
}
