package com.uleeankin.touristrouteselection.activity.attributes.preliminary.service;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.util.List;

public interface PreliminaryActivityService {

    void save(String id, Long activityId, boolean eventStatus);

    void deleteAll(String id);

    void deleteById(String id, Long activityId);

    void updateTime(String id, Long activityId, String time);

    boolean hasEvents(String id);

    List<PreliminaryActivity> getAll(String id);

    List<PreliminaryActivity> getAllPreliminary(String id);
}
