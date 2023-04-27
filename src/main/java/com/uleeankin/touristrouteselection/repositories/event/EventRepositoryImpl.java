package com.uleeankin.touristrouteselection.repositories.event;

import com.uleeankin.touristrouteselection.utils.config.EventConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;

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
}
