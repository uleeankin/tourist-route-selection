package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.config.AgencyRouteConfig;
import com.uleeankin.touristrouteselection.route.mapper.AgencyRouteRowMapper;
import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class AgencyRouteRepositoryImpl implements AgencyRouteRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AgencyRouteConfig config;

    @Autowired
    public AgencyRouteRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     AgencyRouteConfig config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
    }

    @Override
    public AgencyRoute findById(Long id) {
        return this.jdbcTemplate.queryForObject(
                this.config.getById(), new AgencyRouteRowMapper(), id);
    }

    @Override
    public boolean isBooked(Long id) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(
                this.config.getIsBooked(), (rs, rowNum) -> rs.getBoolean(1), id));
    }

    @Override
    public Integer countFreePlaces(Long id, Date date) {
        return this.jdbcTemplate.queryForObject(this.config.getFreePlaces(),
                (rs, rowNum) -> rs.getInt(1), id, date);
    }

    @Override
    public void bookRoute(Long routeId, String userId, Date bookingDate, Integer touristNumber) {
        this.jdbcTemplate.update(this.config.getBookRoute(), routeId, userId, bookingDate, touristNumber);
    }
}
