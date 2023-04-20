package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.route.RouteFeedback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteFeedbackMapper implements RowMapper<RouteFeedback> {
    @Override
    public RouteFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RouteFeedback(rs.getString("user_id"),
                rs.getLong("route_id"),
                rs.getInt("assessment"),
                rs.getString("feedback"),
                rs.getDate("feedback_date"));
    }
}
