package com.uleeankin.touristrouteselection.services.activity;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;

import java.util.List;

public interface PreliminaryActivityService {

    void save(String id, Long activityId);

    void updateStatus(String id, Long activityId, boolean newStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, String time);

    List<PreliminaryRouteActivity> getAll(String id);

    List<PreliminaryRouteActivity> getAllPreliminary(String id);
}
