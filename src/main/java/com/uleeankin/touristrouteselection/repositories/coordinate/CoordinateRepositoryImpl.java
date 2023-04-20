package com.uleeankin.touristrouteselection.repositories.coordinate;

import com.uleeankin.touristrouteselection.models.activity.Coordinate;
import com.uleeankin.touristrouteselection.utils.mappers.CoordinateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoordinateRepositoryImpl implements CoordinateRepository {

    private static final String ADD_COORDINATE =
            "insert into coordinates (latitude, longitude) values (?, ?);";

    private static final String GET_COORDINATE =
            "select coordinates_id, latitude, longitude from coordinates " +
                    "where latitude = ? and longitude = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CoordinateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCoordinate(Double latitude, Double longitude) {
        this.jdbcTemplate.update(ADD_COORDINATE, latitude, longitude);
    }

    @Override
    public Optional<Coordinate> getCoordinate(Double latitude, Double longitude) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                GET_COORDINATE, new CoordinateRowMapper(), latitude, longitude));
    }
}
