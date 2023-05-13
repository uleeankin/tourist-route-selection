package com.uleeankin.touristrouteselection.activity.attributes.preliminary.repository;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.sql.Time;
import java.util.List;

public interface PreliminaryActivityRepository {

    void save(String id, Long activityId, boolean eventStatus);

    void updateEventStatus(String id, Long activityId, boolean newStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, Time time);

    boolean hasEvents(String id, boolean isEvent);

    List<PreliminaryActivity> findAll(String id);

    List<PreliminaryActivity> findAllPreliminary(String id);
}
