package com.uleeankin.touristrouteselection.activity.attributes.event.mapper;

import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.mapper.ActivityMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event(new ActivityMapper().mapRow(rs, rowNum),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getString("event_owner"));
    }
}
