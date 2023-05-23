package com.uleeankin.touristrouteselection.route.mapper;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.route.model.CompletedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompletedRouteRowMapper implements RowMapper<CompletedRoute> {
    @Override
    public CompletedRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompletedRoute(new Route(rs.getLong("route_id"),
                rs.getString("route_name"),
                rs.getString("description"),
                rs.getString("route_owner"),
                rs.getTime("time_duration"),
                rs.getDouble("price"),
                rs.getDouble("path_length"),
                rs.getBoolean("public_status"),
                rs.getDate("creation_date"),
                new City(rs.getLong("city_id"),
                        rs.getString("city_name"))),
                rs.getDate("completion_date"));
    }
}
