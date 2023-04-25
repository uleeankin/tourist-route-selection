package com.uleeankin.touristrouteselection.repositories.activity;

import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.utils.mappers.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    private static final String ADD_ACTIVITY =
            "insert into activities (activity_name, description, " +
                    "coordinates, category, city, price, duration) " +
                    "values (?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_ALL_ACTIVITIES =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, city_id, city_name," +
                    "category_id, category_name, price, duration " +
                    "from ((activity join coordinate on activity.activity_id = coordinate.coordinates_id) " +
                    "join category on activity.category = category.category_id) " +
                    "join city on coordinate.city = city.city_id;";

    private static final String CITY_ACTIVITIES =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, city_id, city_name, " +
                    "category_id, category_name, price, duration " +
                    "from ((activity join coordinate on activity.activity_id = coordinate.coordinates_id) " +
                    "join category on activity.category = category.category_id) " +
                    "join city on coordinate.city = city.city_id where city_name = ?;";

    private static final String ACTIVITY_BY_ID =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, " +
                    "category_id, category_name, city_id, city_name, price, duration " +
                    "from ((activities join coordinates on activities.activity_id = coordinates.coordinates_id) " +
                    "join categories on activities.category = categories.category_id) " +
                    "join city on activities.city = city.city_id where activity_id = ?;";

    private static final String CITY_AND_CATEGORY_ACTIVITIES =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, " +
                    "category_id, category_name, city_id, city_name, price, duration " +
                    "from ((activities join coordinates on activities.activity_id = coordinates.coordinates_id) " +
                    "join categories on activities.category = categories.category_id) " +
                    "join city on activities.city = city.city_id where city_name = ? and category_name = ?;";

    private static final String ADD_TO_FAVOURITES =
            "insert into favourite_activity (user_id, activity_id) values (?, ?);";

    private static final String FAVOURITES =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, " +
                    "category_id, category_name, city_id, city_name, price, duration " +
                    "from ((activities join coordinates on activities.activity_id = coordinates.coordinates_id) " +
                    "join categories on activities.category = categories.category_id) " +
                    "join city on activities.city = city.city_id where " +
                    "activity_id in (select activity_id from favourite_activity where user_id = ?);";

    private static final String FAVOURITES_BY_CITY_AND_CATEGORY =
            "select activity_id, activity_name, description, coordinates_id, latitude, longitude, " +
                    "category_id, category_name, city_id, city_name, price, duration " +
                    "from ((activities join coordinates on activities.activity_id = coordinates.coordinates_id) " +
                    "join categories on activities.category = categories.category_id) " +
                    "join city on activities.city = city.city_id where city_name = ? and category_name = ? and " +
                    "activity_id in (select activity_id from favourite_activity where user_id = ?);";

    private static final String DELETE_FROM_FAVOURITES =
            "delete from favourite_activity where user_id = ? and activity_id = ?;";

    private static final String IS_EXISTS =
            "select exists (select * from favourite_activity where user_id = ? and activity_id = ?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActivityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addActivity(String name, String description, Long city,
                            Long coordinate, Long category, Time time, Double price) {

        this.jdbcTemplate.update(ADD_ACTIVITY, name, description,
                coordinate, category, city, price, time);
    }

    @Override
    public List<Activity> findAll() {
        return this.jdbcTemplate.query(GET_ALL_ACTIVITIES, new ActivityMapper());
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return Optional.ofNullable(
                this.jdbcTemplate.queryForObject(ACTIVITY_BY_ID,
                        new ActivityMapper(), id));
    }

    @Override
    public List<Activity> findByCity(String city) {
        return this.jdbcTemplate.query(CITY_ACTIVITIES, new ActivityMapper(), city);
    }

    @Override
    public List<Activity> findByCityAndCategory(String city, String category) {
        return this.jdbcTemplate.query(CITY_AND_CATEGORY_ACTIVITIES,
                new ActivityMapper(), city, category);
    }

    @Override
    public void addToFavourites(String userId, Long activityId) {
        this.jdbcTemplate.update(ADD_TO_FAVOURITES, userId, activityId);
    }

    @Override
    public void deleteFromFavourites(String userId, Long activityId) {
        this.jdbcTemplate.update(DELETE_FROM_FAVOURITES, userId, activityId);
    }

    @Override
    public List<Activity> findFavourites(String login) {
        return this.jdbcTemplate.query(FAVOURITES, new ActivityMapper(), login);
    }

    @Override
    public List<Activity> findFavouritesByCityAndCategory(
            String login, String city, String category) {
        return this.jdbcTemplate.query(FAVOURITES_BY_CITY_AND_CATEGORY,
                new ActivityMapper(), city, category, login);
    }

    @Override
    public boolean isExists(String userId, Long activityId) {
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(IS_EXISTS, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean(1);
            }
        }, userId, activityId));
    }
}
