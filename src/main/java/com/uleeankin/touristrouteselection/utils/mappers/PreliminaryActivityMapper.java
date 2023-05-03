package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PreliminaryActivityMapper
        implements RowMapper<PreliminaryRouteActivity> {
    @Override
    public PreliminaryRouteActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PreliminaryRouteActivity(
                rs.getString("preliminary_route_id"),
                rs.getLong("activity_id"),
                rs.getBoolean("status"),
                rs.getTime("event_time"));
    }
}
