package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.models.activity.Coordinate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityMapper implements RowMapper<Activity> {
    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Activity(rs.getLong("activity_id"),
                rs.getString("activity_name"),
                rs.getString("description"),
                new Coordinate(rs.getLong("coordinates_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        new City(rs.getLong("city_id"),
                                rs.getString("city_name"))),
                new Category(rs.getLong("category_id"),
                        rs.getString("category_name")),
                rs.getDouble("price"),
                rs.getTime("duration"));
    }
}
