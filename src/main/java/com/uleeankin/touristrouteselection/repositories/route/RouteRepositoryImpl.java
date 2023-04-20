package com.uleeankin.touristrouteselection.repositories.route;

import com.uleeankin.touristrouteselection.models.route.CompletedRoute;
import com.uleeankin.touristrouteselection.models.route.Route;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.utils.mappers.ActivityMapper;
import com.uleeankin.touristrouteselection.utils.mappers.CompletedRouteRowMapper;
import com.uleeankin.touristrouteselection.utils.mappers.RouteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private static final String ADD_ROUTE_ACTIVITY =
            "insert into route_activities (route_id, activity_id) values (?, ?);";

    private static final String ADD_ROUTE =
            "insert into route (route_name, description, route_owner, time_duration," +
                    " price, path_length, creation_date, city) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_ROUTE =
            "select route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name " +
                    "from route join city on route.city = city.city_id " +
                    "where route_name = ? and route_owner = ?;";

    private static final String GET_OWNER_ROUTES =
            "select route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name " +
                    "from route join city on route.city = city.city_id " +
                    "where route_owner = ?;";

    private static final String GET_ROUTE_BY_ID =
            "select route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name " +
                    "from route join city on route.city = city.city_id " +
                    "where route_id = ?;";

    private static final String GET_NOT_OWNER_ROUTES =
            "select route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name " +
                    "from route join city on route.city = city.city_id " +
                    "where route_owner <> ? and public_status;";

    private static final String GET_FAVOURITE_ROUTES =
            "select route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name " +
                    "from route join city on route.city = city.city_id " +
                    "where route_owner <> ? and public_status " +
                    "and route_id in (select route_id from favourite_route where user_id = ?);";

    private static final String GET_COMPLETED_ROUTES =
            "select route.route_id, route_name, description, route_owner, time_duration, price, " +
                    "path_length, public_status, creation_date, city_id, city_name, completion_date " +
                    "from (route join city on route.city = city.city_id) " +
                    "join completed_route on route.route_id = completed_route.route_id " +
                    "where completed_route.user_id = ? and public_status;";

    private static final String GET_ROUTE_ACTIVITIES =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, " +
            "category_id, category_name, city_id, city_name, price, duration " +
            "from ((activities join coordinates on activities.activity_id = coordinates.coordinates_id) " +
            "join categories on activities.category = categories.category_id) " +
            "join city on activities.city = city.city_id where activity_id in " +
                    "(select activity_id from route_activities where route_id = ?);";

    private static final String CHANGE_STATUS =
            "update route set public_status = ? where route_id = ?;";

    private static final String ADD_TO_FAVOURITES =
            "insert into favourite_route (user_id, route_id) values (?, ?);";

    private static final String DELETE_FROM_FAVOURITES =
            "delete from favourite_route where user_id = ? and route_id = ?;";

    private static final String IS_FAVOURITE =
            "select exists (select * from favourite_route where user_id = ? and route_id = ?);";

    private static final String IS_COMPLETED =
            "select exists (select * from completed_route where user_id = ? and route_id = ?);";

    private static final String COMPLETE_ROUTE =
            "insert into completed_route (user_id, route_id, completion_date) values (?, ?, ?);";

    private static final String USES_NUMBER =
            "select getRouteUserNumber(?) as uses_number;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RouteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String name, String description, String owner, Time duration,
                     double price, double length, Date creationDate, Long city) {

        this.jdbcTemplate.update(ADD_ROUTE,
                name, description, owner, duration,
                price, length, creationDate, city);
    }

    @Override
    public void saveActivityToRoute(Long routeId, Long activityId) {
        this.jdbcTemplate.update(ADD_ROUTE_ACTIVITY, routeId, activityId);
    }

    @Override
    public void changeStatus(Long routeId, boolean newStatus) {
        this.jdbcTemplate.update(CHANGE_STATUS, newStatus, routeId);
    }

    @Override
    public Route findByNameAndOwner(String name, String owner) {
        return this.jdbcTemplate.queryForObject(GET_ROUTE, new RouteRowMapper(),
                name, owner);
    }

    @Override
    public List<Activity> findAllByRoute(Long routeId) {
        return this.jdbcTemplate.query(GET_ROUTE_ACTIVITIES, new ActivityMapper(), routeId);
    }

    @Override
    public List<Route> findAllByOwner(String owner) {
        return this.jdbcTemplate.query(GET_OWNER_ROUTES, new RouteRowMapper(), owner);
    }

    @Override
    public List<Route> findAllWithoutOwn(String userLogin) {
        return this.jdbcTemplate.query(GET_NOT_OWNER_ROUTES,
                new RouteRowMapper(), userLogin);
    }

    @Override
    public Route getById(Long routeId) {
        return this.jdbcTemplate.queryForObject(GET_ROUTE_BY_ID,
                new RouteRowMapper(), routeId);
    }

    @Override
    public void addToFavourites(String userId, Long routeId) {
        this.jdbcTemplate.update(ADD_TO_FAVOURITES, userId, routeId);
    }

    @Override
    public void deleteFromFavourites(String userId, Long routeId) {
        this.jdbcTemplate.update(DELETE_FROM_FAVOURITES, userId, routeId);
    }

    @Override
    public boolean isFavourite(String userId, Long routeId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(IS_FAVOURITE, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean(1);
            }
        }, userId, routeId));
    }

    @Override
    public boolean isCompleted(String userId, Long routeId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(IS_COMPLETED, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean(1);
            }
        }, userId, routeId));
    }

    @Override
    public List<Route> findFavourites(String login) {
        return this.jdbcTemplate.query(GET_FAVOURITE_ROUTES,
                new RouteRowMapper(), login, login);
    }

    @Override
    public void completeRoute(String userLogin, Long routeId, Date completionDate) {
        this.jdbcTemplate.update(COMPLETE_ROUTE, userLogin, routeId, completionDate);
    }

    @Override
    public List<CompletedRoute> findCompletedRoutes(String userLogin) {
        return this.jdbcTemplate.query(GET_COMPLETED_ROUTES,
                new CompletedRouteRowMapper(), userLogin);
    }

    @Override
    public Integer getRouteUsesNumber(Long routeId) {
        return this.jdbcTemplate.queryForObject(USES_NUMBER, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("uses_number");
            }
        }, routeId);
    }
}
