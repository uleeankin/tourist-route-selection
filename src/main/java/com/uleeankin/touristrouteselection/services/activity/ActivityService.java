package com.uleeankin.touristrouteselection.services.activity;
import com.uleeankin.touristrouteselection.models.activity.Activity;

import java.util.List;

public interface ActivityService {

    void addActivity(String name, String description,
                     String cityName, String categoryName,
                     Double latitude, Double longitude,
                     String time, Double price);

    List<Activity> getByCity(String cityName);

    List<Activity> getByCityAndCategory(String city, String category);

    void addToFavourites(String userId, Long activityId);

    void deleteFromFavourites(String userId, Long activityId);

    List<Activity> getFavourites(String login);

    List<Activity> getFavouritesByCityAndCategory(
            String login, String city, String category);

    Activity getById(Long id);

    boolean isExists(String userId, Long activityId);
}
