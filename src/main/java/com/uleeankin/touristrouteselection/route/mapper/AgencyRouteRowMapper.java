package com.uleeankin.touristrouteselection.route.mapper;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyRouteRowMapper implements RowMapper<AgencyRoute> {
    @Override
    public AgencyRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AgencyRoute(new RouteRowMapper().mapRow(rs, rowNum),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getInt("max_tourist_number"));
    }
}
