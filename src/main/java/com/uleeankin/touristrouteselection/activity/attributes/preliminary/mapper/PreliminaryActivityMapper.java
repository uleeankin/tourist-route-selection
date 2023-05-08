package com.uleeankin.touristrouteselection.activity.attributes.preliminary.mapper;

import com.uleeankin.touristrouteselection.activity.mapper.ActivityMapper;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PreliminaryActivityMapper
        implements RowMapper<PreliminaryRouteActivity> {
    @Override
    public PreliminaryRouteActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PreliminaryRouteActivity(
                rs.getString("preliminary_route_id"),
                new ActivityMapper().mapRow(rs, rowNum),
                rs.getBoolean("is_compulsory"),
                rs.getBoolean("is_event"),
                rs.getTime("event_time"));
    }
}
