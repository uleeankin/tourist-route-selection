package com.uleeankin.touristrouteselection.activity.repository;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.config.ActivityConfig;
import com.uleeankin.touristrouteselection.activity.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    private final ActivityConfig activityConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActivityRepositoryImpl(ActivityConfig activityConfig, JdbcTemplate jdbcTemplate) {
        this.activityConfig = activityConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addActivity(String name, String description,
                            Long coordinate, Long category, byte[] photo,
                            Time time, Double price) {

        this.jdbcTemplate.update(this.activityConfig.getActivityAdding(),
                name, description, coordinate, category, photo, price, time);
    }

    @Override
    public List<Activity> findAll() {
        return this.jdbcTemplate.query(
                this.activityConfig.getAll(), new ActivityMapper());
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return Optional.ofNullable(
                this.jdbcTemplate.queryForObject(
                        this.activityConfig.getById(),new ActivityMapper(), id));
    }

    @Override
    public List<Activity> findByCity(String city) {
        return this.jdbcTemplate.query(
                this.activityConfig.getByCity(), new ActivityMapper(), city);
    }

    @Override
    public List<Activity> findByCityAndCategory(String city, String category) {
        return this.jdbcTemplate.query(this.activityConfig.getByCityAndCategory(),
                new ActivityMapper(), city, category);
    }

    @Override
    public void addToFavourites(String userId, Long activityId) {
        this.jdbcTemplate.update(
                this.activityConfig.getToFavourite(), userId, activityId);
    }

    @Override
    public void deleteFromFavourites(String userId, Long activityId) {
        this.jdbcTemplate.update(
                this.activityConfig.getDeleteFromFavouriteQuery(), userId, activityId);
    }

    @Override
    public List<Activity> findFavourites(String login) {
        return this.jdbcTemplate.query(
                this.activityConfig.getAllFavourites(), new ActivityMapper(), login);
    }

    @Override
    public List<Activity> findFavouritesByCityAndCategory(
            String login, String city, String category) {
        return this.jdbcTemplate.query(this.activityConfig.getFavouritesByCityAndCategory(),
                new ActivityMapper(), city, category, login);
    }

    @Override
    public boolean isExists(String userId, Long activityId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(
                this.activityConfig.getExistence(),
                (rs, rowNum) -> rs.getBoolean(1), userId, activityId));
    }

    @Override
    public void update(Long id, String name, String description, Time time, Double price) {
        this.jdbcTemplate.update(this.activityConfig.getUpdate(),
                name, description, time, price, id);
    }

    @Override
    public void deleteById(Long id) {
        this.jdbcTemplate.update(this.activityConfig.getDeleteById(), id);
    }

    @Override
    public Optional<Activity> findByCoordinates(String name, Double latitude, Double longitude) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                this.activityConfig.getByCoordinates(), new ActivityMapper(), name, latitude, longitude));
    }
}
