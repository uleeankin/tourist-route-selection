package com.uleeankin.touristrouteselection.repositories.event;

import com.uleeankin.touristrouteselection.models.activity.Event;
import com.uleeankin.touristrouteselection.utils.config.EventConfig;
import com.uleeankin.touristrouteselection.utils.mappers.EventRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EventConfig eventConfig;

    @Autowired
    public EventRepositoryImpl(JdbcTemplate jdbcTemplate, EventConfig eventConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventConfig = eventConfig;
    }

    @Override
    public void save(Long id, Date startDate, Date endDate, String owner) {
        this.jdbcTemplate.update(this.eventConfig.getSave(),
                id, startDate, endDate, owner);
    }

    @Override
    public void saveSession(Long id, Time sessionTime) {
        this.jdbcTemplate.update(this.eventConfig.getSaveSession(),
                id, sessionTime);
    }

    @Override
    public List<Event> findAll() {
        return this.jdbcTemplate.query(
                this.eventConfig.getAll(), new EventRowMapper());
    }

    @Override
    public Event findById(Long id) {
        return this.jdbcTemplate.queryForObject(
                this.eventConfig.getById(), new EventRowMapper(), id);
    }

    @Override
    public void deleteById(Long id) {
        this.jdbcTemplate.update(this.eventConfig.getDelete(), id);
    }

    @Override
    public void deleteSessions(Long id) {
        this.jdbcTemplate.update(this.eventConfig.getDeleteSession(), id);
    }

    @Override
    public void update(Long id, Date startDate, Date endDate) {
        this.jdbcTemplate.update(this.eventConfig.getUpdate(),
                startDate, endDate, id);
    }
}
