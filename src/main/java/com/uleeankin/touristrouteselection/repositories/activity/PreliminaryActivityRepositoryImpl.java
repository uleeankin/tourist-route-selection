package com.uleeankin.touristrouteselection.repositories.activity;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.utils.config.PreliminaryActivityConfig;
import com.uleeankin.touristrouteselection.utils.mappers.PreliminaryActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public void save(String id, Long activityId) {
        this.jdbcTemplate.update(this.config.getSave(), id, activityId);
    }

    @Override
    public void updateStatus(String id, Long activityId, boolean newStatus) {
        this.jdbcTemplate.update(
                this.config.getUpdateStatus(), newStatus, id, activityId);
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
