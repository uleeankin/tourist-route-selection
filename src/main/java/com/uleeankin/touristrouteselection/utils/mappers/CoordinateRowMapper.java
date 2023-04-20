package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.activity.Coordinate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordinateRowMapper implements RowMapper<Coordinate> {
    @Override
    public Coordinate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Coordinate(rs.getLong("coordinates_id"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"));
    }
}
