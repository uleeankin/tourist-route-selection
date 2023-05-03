package com.uleeankin.touristrouteselection.repositories.activity;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;

import java.sql.Time;
import java.util.List;

public interface PreliminaryActivityRepository {

    void save(String id, Long activityId);

    void updateStatus(String id, Long activityId, boolean newStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, Time time);

    List<PreliminaryRouteActivity> findAll(String id);

    List<PreliminaryRouteActivity> findAllPreliminary(String id);
}
