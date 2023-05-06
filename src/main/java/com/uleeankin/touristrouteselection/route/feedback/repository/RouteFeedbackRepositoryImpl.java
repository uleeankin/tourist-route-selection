package com.uleeankin.touristrouteselection.route.feedback.repository;

import com.uleeankin.touristrouteselection.route.feedback.model.RouteFeedback;
import com.uleeankin.touristrouteselection.route.mapper.RouteFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RouteFeedbackRepositoryImpl implements RouteFeedbackRepository {

    private static final String ADD_FEEDBACK =
            "insert into route_feedback (user_id, route_id, assessment, feedback, feedback_date) " +
                    "values (?, ?, ?, ?, ?);";

    private static final String GET_ALL_FEEDBACK =
            "select user_id, route_id, assessment, feedback, feedback_date " +
                    "from route_feedback where route_id = ?;";

    private static final String COUNT_FEEDBACK =
            "select getRouteFeedbackNumber(?) as feedback_number;";

    private static final String AVERAGE_ASSESSMENT =
            "select getRouteAverageAssessment(?) as average_assessment;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RouteFeedbackRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RouteFeedback> findAllById(Long routeId) {
        return this.jdbcTemplate.query(GET_ALL_FEEDBACK,
                new RouteFeedbackMapper(), routeId);
    }

    @Override
    public void addFeedback(String userLogin, Long routeId,
                            Integer assessment, String feedback, Date creationDate) {
        this.jdbcTemplate.update(ADD_FEEDBACK, userLogin, routeId,
                assessment, feedback, creationDate);
    }

    @Override
    public Integer countFeedbackNumber(Long routeId) {
        return this.jdbcTemplate.queryForObject(COUNT_FEEDBACK, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("feedback_number");
            }
        }, routeId);
    }

    @Override
    public Double calculateAverageAssessment(Long routeId) {
        return this.jdbcTemplate.queryForObject(AVERAGE_ASSESSMENT, new RowMapper<Double>() {
            @Override
            public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getDouble("average_assessment");
            }
        }, routeId);
    }
}
