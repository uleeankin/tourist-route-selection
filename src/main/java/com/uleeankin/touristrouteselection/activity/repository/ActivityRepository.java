package com.uleeankin.touristrouteselection.activity.repository;

import com.uleeankin.touristrouteselection.activity.model.Activity;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository {

    void addActivity(String name, String description,
                     Long coordinate, Long category,
                     byte[] photo, Time time, Double price);

    List<Activity> findAll();

    Optional<Activity> findById(Long id);

    List<Activity> findByCity(String city);

    List<Activity> findByCityAndCategory(String city, String category);

    void addToFavourites(String userId, Long activityId);

    void deleteFromFavourites(String userId, Long activityId);

    List<Activity> findFavourites(String login);

    List<Activity> findFavouritesByCityAndCategory(String login,
                                                   String city,
                                                   String category);

    boolean isExists(String userId, Long activityId);

    void update(Long id, String name, String description, Time time, Double price, byte[] photo);

    void deleteById(Long id);

    Optional<Activity> findByCoordinates(String name, Double latitude, Double longitude);
}
