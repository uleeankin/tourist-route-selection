package com.uleeankin.touristrouteselection.activity.attributes.coordinates.mapper;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordinateRowMapper implements RowMapper<Coordinate> {
    @Override
    public Coordinate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Coordinate(rs.getLong("coordinates_id"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                new City(rs.getLong("city_id"),
                        rs.getString("city_name")));
    }
}
