package com.uleeankin.touristrouteselection.activity.attributes.preliminary.repository;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.config.PreliminaryActivityConfig;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.mapper.PreliminaryActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@Repository
public class PreliminaryActivityRepositoryImpl implements PreliminaryActivityRepository {

    private final PreliminaryActivityConfig config;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PreliminaryActivityRepositoryImpl(
            PreliminaryActivityConfig config, JdbcTemplate jdbcTemplate) {
        this.config = config;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String id, Long activityId, boolean eventStatus) {

        this.jdbcTemplate.update(this.config.getSave(), id, activityId, eventStatus);
    }

    @Override
    public void updateCompulsoryStatus(String id, Long activityId, boolean newStatus) {
        this.jdbcTemplate.update(
                this.config.getUpdateCompulsoryStatus(), newStatus, id, activityId);
    }

    @Override
    public boolean findCompulsoryStatus(String id, Long activityId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(this.config.getCompulsoryStatus(),
                new RowMapper<Boolean>() {
                    @Override
                    public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getBoolean("is_compulsory");
                    }
                }, id, activityId));
    }

    @Override
    public void updateEventStatus(String id, Long activityId, boolean newStatus) {
        this.jdbcTemplate.update(
                this.config.getUpdateEventStatus(), newStatus, id, activityId);
    }

    @Override
    public void deleteAll(String id) {
        this.jdbcTemplate.update(this.config.getDeleteAll(), id);
    }

    @Override
    public void deleteById(String id, Long activityId) {
        this.jdbcTemplate.update(this.config.getDeleteById(), id, activityId);
    }

    @Override
    public void updateTime(String id, Long activityId, Time time) {
        this.jdbcTemplate.update(this.config.getUpdateTime(), time, id, activityId);
    }

    @Override
    public boolean hasEvents(String id, boolean isEvent) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(
                this.config.getHasEvents(), new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean("event_existing");
            }
        }, id, isEvent));
    }

    @Override
    public List<PreliminaryRouteActivity> findAll(String id) {
        return this.jdbcTemplate.query(this.config.getAll(),
                new PreliminaryActivityMapper(), id);
    }

    @Override
    public List<PreliminaryRouteActivity> findAllPreliminary(String id) {
        return this.jdbcTemplate.query(this.config.getAllPreliminary(),
                new PreliminaryActivityMapper(), id, true);
    }
}
