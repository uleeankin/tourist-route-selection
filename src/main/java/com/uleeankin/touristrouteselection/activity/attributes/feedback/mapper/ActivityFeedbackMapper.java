package com.uleeankin.touristrouteselection.activity.attributes.feedback.mapper;

import com.uleeankin.touristrouteselection.activity.attributes.feedback.model.ActivityFeedback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityFeedbackMapper implements RowMapper<ActivityFeedback> {
    @Override
    public ActivityFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ActivityFeedback(rs.getString("user_id"),
                rs.getLong("activity_id"),
                rs.getInt("assessment"),
                rs.getString("feedback"),
                rs.getDate("feedback_date"));
    }
}
