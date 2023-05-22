package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.config.RouteConfig;
import com.uleeankin.touristrouteselection.route.model.CompletedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.mapper.ActivityMapper;
import com.uleeankin.touristrouteselection.route.mapper.CompletedRouteRowMapper;
import com.uleeankin.touristrouteselection.route.mapper.RouteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private final RouteConfig routeConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RouteRepositoryImpl(RouteConfig routeConfig,
                               JdbcTemplate jdbcTemplate) {
        this.routeConfig = routeConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String name, String description, String owner, Time duration,
                     double price, double length, Date creationDate, byte[] photo, Long city) {

        this.jdbcTemplate.update(this.routeConfig.getSave(),
                name, description, owner, duration,
                price, length, creationDate, photo, city);
    }

    @Override
    public void saveActivityToRoute(Long routeId, Long activityId) {
        this.jdbcTemplate.update(this.routeConfig.getAddActivity(),
                routeId, activityId);
    }

    @Override
    public void changeStatus(Long routeId, boolean newStatus) {
        this.jdbcTemplate.update(
                this.routeConfig.getStatusChanging(), newStatus, routeId);
    }

    @Override
    public Route findByNameAndOwner(String name, String owner) {
        return this.jdbcTemplate.queryForObject(
                this.routeConfig.getByNameAndOwner(),
                new RouteRowMapper(), name, owner);
    }

    @Override
    public List<Activity> findAllByRoute(Long routeId) {
        return this.jdbcTemplate.query(this.routeConfig.getActivities(),
                new ActivityMapper(), routeId);
    }

    @Override
    public List<Route> findAllByOwner(String owner) {
        return this.jdbcTemplate.query(this.routeConfig.getByOwner(),
                new RouteRowMapper(), owner);
    }

    @Override
    public List<Route> findAllWithoutOwn(String userLogin) {
        return this.jdbcTemplate.query(
                this.routeConfig.getNotOwner(),
                new RouteRowMapper(), userLogin);
    }

    @Override
    public Route getById(Long routeId) {
        return this.jdbcTemplate.queryForObject(
                this.routeConfig.getById(),
                new RouteRowMapper(), routeId);
    }

    @Override
    public void addToFavourites(String userId, Long routeId) {
        this.jdbcTemplate.update(
                this.routeConfig.getAddFavourite(), userId, routeId);
    }

    @Override
    public void deleteFromFavourites(String userId, Long routeId) {
        this.jdbcTemplate.update(
                this.routeConfig.getDeleteFavourite(), userId, routeId);
    }

    @Override
    public boolean isFavourite(String userId, Long routeId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(
                this.routeConfig.getIsFavourite(), new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean(1);
            }
        }, userId, routeId));
    }

    @Override
    public boolean isCompleted(String userId, Long routeId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(
                this.routeConfig.getIsCompleted(), new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean(1);
            }
        }, userId, routeId));
    }

    @Override
    public List<Route> findFavourites(String login) {
        return this.jdbcTemplate.query(
                this.routeConfig.getFavourites(),
                new RouteRowMapper(), login, login);
    }

    @Override
    public void completeRoute(String userLogin, Long routeId, Date completionDate) {
        this.jdbcTemplate.update(
                this.routeConfig.getComplete(), userLogin, routeId, completionDate);
    }

    @Override
    public List<CompletedRoute> findCompletedRoutes(String userLogin) {
        return this.jdbcTemplate.query(
                this.routeConfig.getCompleted(),
                new CompletedRouteRowMapper(), userLogin);
    }

    @Override
    public Integer getRouteUsesNumber(Long routeId) {
        return this.jdbcTemplate.queryForObject(
                this.routeConfig.getUsesNumber(), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("uses_number");
            }
        }, routeId);
    }

    @Override
    public void saveAgencyRoute(Long id, Date startDate,
                                Date endDate, Integer maxTouristsNumber) {
        this.jdbcTemplate.update(this.routeConfig.getSaveAgency(),
                id, startDate, endDate, maxTouristsNumber);
    }
}
