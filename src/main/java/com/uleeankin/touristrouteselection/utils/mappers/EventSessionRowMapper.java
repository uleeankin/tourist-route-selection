package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.activity.EventSession;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventSessionRowMapper implements RowMapper<EventSession> {
    @Override
    public EventSession mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventSession(
                rs.getLong("event_id"),
                rs.getTime("session_time"));
    }
}
