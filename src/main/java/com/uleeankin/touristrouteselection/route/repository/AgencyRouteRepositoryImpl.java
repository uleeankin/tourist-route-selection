package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.config.AgencyRouteConfig;
import com.uleeankin.touristrouteselection.route.mapper.AgencyRouteRowMapper;
import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
