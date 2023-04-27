package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.activity.Event;
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
