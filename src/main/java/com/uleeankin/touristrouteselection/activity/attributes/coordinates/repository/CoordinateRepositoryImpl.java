package com.uleeankin.touristrouteselection.activity.attributes.coordinates.repository;

import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.config.CoordinateConfig;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.mapper.CoordinateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoordinateRepositoryImpl implements CoordinateRepository {
    private final CoordinateConfig coordinateConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CoordinateRepositoryImpl(CoordinateConfig coordinateConfig, JdbcTemplate jdbcTemplate) {
        this.coordinateConfig = coordinateConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCoordinate(Double latitude, Double longitude, Long cityId) {
        this.jdbcTemplate.update(this.coordinateConfig.getAdding(),
                latitude, longitude, cityId);
    }

    @Override
    public Optional<Coordinate> getCoordinate(Double latitude, Double longitude) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                this.coordinateConfig.getObject(), new CoordinateRowMapper(), latitude, longitude));
    }

    @Override
    public void deleteById(Long id) {
        this.jdbcTemplate.update(this.coordinateConfig.getDelete(), id);
    }
}
