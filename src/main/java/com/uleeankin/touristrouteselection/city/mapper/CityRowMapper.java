package com.uleeankin.touristrouteselection.city.mapper;

import com.uleeankin.touristrouteselection.city.model.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new City(rs.getLong("city_id"),
                rs.getString("city_name"));
    }
}
