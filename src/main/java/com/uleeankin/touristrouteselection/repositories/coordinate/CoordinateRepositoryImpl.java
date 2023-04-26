package com.uleeankin.touristrouteselection.repositories.coordinate;

import com.uleeankin.touristrouteselection.models.activity.Coordinate;
import com.uleeankin.touristrouteselection.utils.config.CoordinateConfig;
import com.uleeankin.touristrouteselection.utils.mappers.CoordinateRowMapper;
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
}
