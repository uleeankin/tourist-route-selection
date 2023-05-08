package com.uleeankin.touristrouteselection.activity.attributes.preliminary.service;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;

import java.util.List;

public interface PreliminaryActivityService {

    void save(String id, Long activityId, boolean eventStatus);

    void updateCompulsoryStatus(String id, Long activityId);

    void updateEventStatus(String id, Long activityId, boolean newStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, String time);

    List<PreliminaryRouteActivity> getAll(String id);

    List<PreliminaryRouteActivity> getAllPreliminary(String id);
}
