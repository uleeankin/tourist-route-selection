package com.uleeankin.touristrouteselection.repositories.city;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.utils.mappers.CityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepositoryImpl implements CityRepository {

    private static final String ALL_CITIES_QUERY
            = "select city_id, city_name from city;";

    private static final String CITY_BY_NAME_QUERY
            = "select city_id, city_name from city where city_name = ?;";

    private static final String SAVE_BY_NAME
            = "insert into city (city_name) values (?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String cityName) {
        this.jdbcTemplate.update(SAVE_BY_NAME, cityName);
    }

    @Override
    public Optional<City> findByName(String cityName) {
        try {
            City city = this.jdbcTemplate.queryForObject(
                    CITY_BY_NAME_QUERY, new CityRowMapper(), cityName);
            return Optional.ofNullable(city);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<City> findAll() {
        return this.jdbcTemplate.query(ALL_CITIES_QUERY, new CityRowMapper());
    }
}
