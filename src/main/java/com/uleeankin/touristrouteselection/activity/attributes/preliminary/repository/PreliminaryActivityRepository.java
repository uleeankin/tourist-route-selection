package com.uleeankin.touristrouteselection.activity.attributes.preliminary.repository;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;

import java.sql.Time;
import java.util.List;

public interface PreliminaryActivityRepository {

    void save(String id, Long activityId, boolean eventStatus);

    void updateCompulsoryStatus(String id, Long activityId, boolean newStatus);

    boolean findCompulsoryStatus(String id, Long activityId);

    void updateEventStatus(String id, Long activityId, boolean newStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, Time time);

    List<PreliminaryRouteActivity> findAll(String id);

    List<PreliminaryRouteActivity> findAllPreliminary(String id);
}
