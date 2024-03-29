package com.uleeankin.touristrouteselection.activity.service;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.service.PreliminaryActivityService;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.model.ActivityStatus;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ActivityService {

    void addActivity(String name, String description,
                     String cityName, String categoryName,
                     Double latitude, Double longitude,
                     String time, Double price, byte[] photo);

    List<Activity> getByCity(String cityName);

    List<Activity> getByCityAndCategory(String city, String category);

    void addToFavourites(String userId, Long activityId);

    Activity getActivity(String name, Double latitude, Double longitude);

    void deleteFromFavourites(String userId, Long activityId);

    List<Activity> getFavourites(String login);

    List<Activity> getFavouritesByCityAndCategory(
            String login, String city, String category);

    Activity getById(Long id);

    boolean isExists(String userId, Long activityId);

    void update(Long id, String name, String description, String time, Double price, byte[] bytes);

    void delete(Long id);

    List<ActivityStatus> getActivityStatuses(List<Activity> activities, HttpSession session,
                                             PreliminaryActivityService preliminaryActivityService);
}
