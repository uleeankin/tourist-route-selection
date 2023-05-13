package com.uleeankin.touristrouteselection.activity.attributes.preliminary.mapper;

import com.uleeankin.touristrouteselection.activity.mapper.ActivityMapper;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PreliminaryActivityMapper
        implements RowMapper<PreliminaryActivity> {
    @Override
    public PreliminaryActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PreliminaryActivity(
                rs.getString("preliminary_route_id"),
                new ActivityMapper().mapRow(rs, rowNum),
                rs.getBoolean("is_event"),
                rs.getTime("event_time"));
    }
}
