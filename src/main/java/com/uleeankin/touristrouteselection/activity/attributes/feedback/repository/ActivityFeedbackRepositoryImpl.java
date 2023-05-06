package com.uleeankin.touristrouteselection.activity.attributes.feedback.repository;

import com.uleeankin.touristrouteselection.activity.attributes.feedback.mapper.ActivityFeedbackMapper;
import com.uleeankin.touristrouteselection.activity.attributes.feedback.model.ActivityFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ActivityFeedbackRepositoryImpl implements ActivityFeedbackRepository {

    private static final String ADD_FEEDBACK =
            "insert into activity_feedback (user_id, activity_id, assessment, feedback, feedback_date) " +
                    "values (?, ?, ?, ?, ?);";

    private static final String GET_ALL_FEEDBACK =
            "select user_id, activity_id, assessment, feedback, feedback_date " +
                    "from activity_feedback where activity_id = ?;";

    private static final String COUNT_FEEDBACK =
            "select getFeedbackNumber(?) as feedback_number;";

    private static final String AVERAGE_ASSESSMENT =
            "select getAverageAssessment(?) as average_assessment;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActivityFeedbackRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ActivityFeedback> findAllById(Long activityId) {

        return this.jdbcTemplate.query(GET_ALL_FEEDBACK,
                new ActivityFeedbackMapper(), activityId);
    }

    @Override
    public void addFeedback(String userLogin, Long activityId,
                            Integer assessment, String feedback,
                            Date creationDate) {
        this.jdbcTemplate.update(ADD_FEEDBACK, userLogin, activityId,
                assessment, feedback, creationDate);
    }

    @Override
    public Integer countFeedbackNumber(Long activityId) {
        return this.jdbcTemplate.queryForObject(COUNT_FEEDBACK, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("feedback_number");
            }
        }, activityId);
    }

    @Override
    public Double calculateAverageAssessment(Long activityId) {
        return this.jdbcTemplate.queryForObject(AVERAGE_ASSESSMENT, new RowMapper<Double>() {
            @Override
            public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getDouble("average_assessment");
            }
        }, activityId);
    }
}
