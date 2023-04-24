package com.uleeankin.touristrouteselection.repositories.city;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.utils.config.CityQueryConfigurator;
import com.uleeankin.touristrouteselection.utils.mappers.CityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private final CityQueryConfigurator cityQueryConfigurator;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityRepositoryImpl(CityQueryConfigurator cityQueryConfigurator, JdbcTemplate jdbcTemplate) {
        this.cityQueryConfigurator = cityQueryConfigurator;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String cityName) {
        this.jdbcTemplate.update(
                this.cityQueryConfigurator.save, cityName);
    }

    @Override
    public Optional<City> findByName(String cityName) {
        try {
            City city = this.jdbcTemplate.queryForObject(
                    this.cityQueryConfigurator.one, new CityRowMapper(), cityName);
            return Optional.ofNullable(city);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<City> findAll() {
        return this.jdbcTemplate.query(
                this.cityQueryConfigurator.all, new CityRowMapper());
    }
}
