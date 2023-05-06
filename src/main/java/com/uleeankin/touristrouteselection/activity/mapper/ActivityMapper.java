package com.uleeankin.touristrouteselection.activity.mapper;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
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
                rs.getBytes("photo"),
                rs.getDouble("price"),
                rs.getTime("duration"));
    }
}
